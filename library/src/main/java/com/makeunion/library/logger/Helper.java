package com.makeunion.library.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * Created by renjialiang on 2017/7/14.
 */

final class Helper {
    private Helper() {
    }

    static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    static boolean equals(CharSequence a, CharSequence b) {
        if(a == b) {
            return true;
        } else {
            if(a != null && b != null) {
                int length = a.length();
                if(length == b.length()) {
                    if(a instanceof String && b instanceof String) {
                        return a.equals(b);
                    }

                    for(int i = 0; i < length; ++i) {
                        if(a.charAt(i) != b.charAt(i)) {
                            return false;
                        }
                    }

                    return true;
                }
            }

            return false;
        }
    }

    static String getStackTraceString(Throwable tr) {
        if(tr == null) {
            return "";
        } else {
            for(Throwable t = tr; t != null; t = t.getCause()) {
                if(t instanceof UnknownHostException) {
                    return "";
                }
            }

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            tr.printStackTrace(pw);
            pw.flush();
            return sw.toString();
        }
    }
}