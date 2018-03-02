package com.makeunion.library.logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by renjialiang on 2017/7/14.
 */

final class LoggerPrinter implements Printer {
    private static final String DEFAULT_TAG = "mime";
    private static final int DEBUG = 3;
    private static final int ERROR = 6;
    private static final int ASSERT = 7;
    private static final int INFO = 4;
    private static final int VERBOSE = 2;
    private static final int WARN = 5;
    private static final int CHUNK_SIZE = 4000;
    private static final int JSON_INDENT = 2;
    private static final int MIN_STACK_OFFSET = 3;
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = "╔════════════════════════════════════════════════════════════════════════════════════════";
    private static final String BOTTOM_BORDER = "╚════════════════════════════════════════════════════════════════════════════════════════";
    private static final String MIDDLE_BORDER = "╟────────────────────────────────────────────────────────────────────────────────────────";
    private String tag;
    private final ThreadLocal<String> localTag = new ThreadLocal();
    private final ThreadLocal<Integer> localMethodCount = new ThreadLocal();
    private final Settings settings = new Settings();

    public LoggerPrinter() {
        this.init("mime");
    }

    public Settings init(String tag) {
        if(tag == null) {
            throw new NullPointerException("tag may not be null");
        } else if(tag.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        } else {
            this.tag = tag;
            return this.settings;
        }
    }

    public Settings getSettings() {
        return this.settings;
    }

    public Printer t(String tag, int methodCount) {
        if(tag != null) {
            this.localTag.set(tag);
        }

        this.localMethodCount.set(Integer.valueOf(methodCount));
        return this;
    }

    public void d(String message, Object... args) {
        this.log(3, (Throwable)null, message, (Object[])args);
    }

    public void d(Object object) {
        String message;
        if(object.getClass().isArray()) {
            message = Arrays.deepToString((Object[])((Object[])object));
        } else {
            message = object.toString();
        }

        this.log(3, (Throwable)null, message, (Object[])(new Object[0]));
    }

    public void e(String message, Object... args) {
        this.e((Throwable)null, message, args);
    }

    public void e(Throwable throwable, String message, Object... args) {
        this.log(6, (Throwable)throwable, message, (Object[])args);
    }

    public void w(String message, Object... args) {
        this.log(5, (Throwable)null, message, (Object[])args);
    }

    public void i(String message, Object... args) {
        this.log(4, (Throwable)null, message, (Object[])args);
    }

    public void v(String message, Object... args) {
        this.log(2, (Throwable)null, message, (Object[])args);
    }

    public void wtf(String message, Object... args) {
        this.log(7, (Throwable)null, message, (Object[])args);
    }

    public void json(String json) {
        if(Helper.isEmpty(json)) {
            this.d("Empty/Null json content");
        } else {
            try {
                json = json.trim();
                JSONObject e;
                String message;
                if(json.contains("{")) {
                    e = new JSONObject(json.substring(json.indexOf("{")));
                    message = e.toString(2);
                    this.d(json.substring(0, json.indexOf("{")) + "\n" + message);
                    return;
                }

                if(json.contains("[")) {
                    e = new JSONObject(json.substring(json.indexOf("[")));
                    message = e.toString(2);
                    this.d(json.substring(0, json.indexOf("[")) + "\n" + message);
                    return;
                }

                this.d(json);
            } catch (JSONException var4) {
                this.d(json);
            }

        }
    }

    public void xml(String xml) {
        if(Helper.isEmpty(xml)) {
            this.d("Empty/Null xml content");
        } else {
            try {
                StreamSource e = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("indent", "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(e, xmlOutput);
                this.d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
            } catch (TransformerException var5) {
                this.e("Invalid xml", new Object[0]);
            }

        }
    }

    public synchronized void log(int priority, String tag, String message, Throwable throwable) {
        if(this.settings.getLogLevel() != LogLevel.NONE) {
            if(throwable != null && message != null) {
                message = message + " : " + Helper.getStackTraceString(throwable);
            }

            if(throwable != null && message == null) {
                message = Helper.getStackTraceString(throwable);
            }

            if(message == null) {
                message = "No message/exception is set";
            }

            int methodCount = this.getMethodCount();
            if(Helper.isEmpty(message)) {
                message = "Empty/NULL log message";
            }

            this.logTopBorder(priority, tag);
            this.logHeaderContent(priority, tag, methodCount);
            byte[] bytes = message.getBytes();
            int length = bytes.length;
            if(length <= 4000) {
                if(methodCount > 0) {
                    this.logDivider(priority, tag);
                }

                this.logContent(priority, tag, message);
                this.logBottomBorder(priority, tag);
            } else {
                if(methodCount > 0) {
                    this.logDivider(priority, tag);
                }

                for(int i = 0; i < length; i += 4000) {
                    int count = Math.min(length - i, 4000);
                    this.logContent(priority, tag, new String(bytes, i, count));
                }

                this.logBottomBorder(priority, tag);
            }
        }
    }

    public void resetSettings() {
        this.settings.reset();
    }

    private synchronized void log(int priority, Throwable throwable, String msg, Object... args) {
        if(this.settings.getLogLevel() != LogLevel.NONE) {
            String tag = this.getTag();
            String message = this.createMessage(msg, args);
            this.log(priority, tag, message, throwable);
        }
    }

    private void logTopBorder(int logType, String tag) {
        this.logChunk(logType, tag, "╔════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void logHeaderContent(int logType, String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if(this.settings.isShowThreadInfo()) {
            this.logChunk(logType, tag, "║ Thread: " + Thread.currentThread().getName());
            this.logDivider(logType, tag);
        }

        String level = "";
        int stackOffset = this.getStackOffset(trace) + this.settings.getMethodOffset();
        if(methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for(int i = methodCount; i > 0; --i) {
            int stackIndex = i + stackOffset;
            if(stackIndex < trace.length) {
                StringBuilder builder = new StringBuilder();
                builder.append("║ ").append(level).append(this.getSimpleClassName(trace[stackIndex].getClassName())).append(".").append(trace[stackIndex].getMethodName()).append(" ").append(" (").append(trace[stackIndex].getFileName()).append(":").append(trace[stackIndex].getLineNumber()).append(")");
                level = level + "   ";
                this.logChunk(logType, tag, builder.toString());
            }
        }

    }

    private void logBottomBorder(int logType, String tag) {
        this.logChunk(logType, tag, "╚════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void logDivider(int logType, String tag) {
        this.logChunk(logType, tag, "╟────────────────────────────────────────────────────────────────────────────────────────");
    }

    private void logContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        String[] var5 = lines;
        int var6 = lines.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String line = var5[var7];
            this.logChunk(logType, tag, "║ " + line);
        }

    }

    private void logChunk(int logType, String tag, String chunk) {
        String finalTag = this.formatTag(tag);
        switch(logType) {
            case 2:
                this.settings.getLogAdapter().v(finalTag, chunk);
                break;
            case 3:
            default:
                this.settings.getLogAdapter().d(finalTag, chunk);
                break;
            case 4:
                this.settings.getLogAdapter().i(finalTag, chunk);
                break;
            case 5:
                this.settings.getLogAdapter().w(finalTag, chunk);
                break;
            case 6:
                this.settings.getLogAdapter().e(finalTag, chunk);
                break;
            case 7:
                this.settings.getLogAdapter().wtf(finalTag, chunk);
        }

    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private String formatTag(String tag) {
        return !Helper.isEmpty(tag) && !Helper.equals(this.tag, tag)?this.tag + "-" + tag:this.tag;
    }

    private String getTag() {
        String tag = (String)this.localTag.get();
        if(tag != null) {
            this.localTag.remove();
            return tag;
        } else {
            return this.tag;
        }
    }

    private String createMessage(String message, Object... args) {
        return args != null && args.length != 0?String.format(message, args):message;
    }

    private int getMethodCount() {
        Integer count = (Integer)this.localMethodCount.get();
        int result = this.settings.getMethodCount();
        if(count != null) {
            this.localMethodCount.remove();
            result = count.intValue();
        }

        if(result < 0) {
            throw new IllegalStateException("methodCount cannot be negative");
        } else {
            return result;
        }
    }

    private int getStackOffset(StackTraceElement[] trace) {
        for(int i = 3; i < trace.length; ++i) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if(!name.equals(LoggerPrinter.class.getName()) && !name.equals(LogHelper.class.getName())) {
                --i;
                return i;
            }
        }

        return -1;
    }
}