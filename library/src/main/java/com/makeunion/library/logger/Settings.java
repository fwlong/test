package com.makeunion.library.logger;

/**
 * Created by renjialiang on 2017/7/14.
 */

public final class Settings {
    private int methodCount = 1;
    private boolean showThreadInfo = false;
    private int methodOffset = 1;
    private LogAdapter logAdapter;
    private LogLevel logLevel;

    public Settings() {
        this.logLevel = LogLevel.FULL;
    }

    public Settings hideThreadInfo() {
        this.showThreadInfo = false;
        return this;
    }

    public Settings methodCount(int methodCount) {
        if(methodCount < 0) {
            methodCount = 0;
        }

        this.methodCount = methodCount;
        return this;
    }

    public Settings logLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public Settings methodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public Settings logAdapter(LogAdapter logAdapter) {
        this.logAdapter = logAdapter;
        return this;
    }

    public int getMethodCount() {
        return this.methodCount;
    }

    public boolean isShowThreadInfo() {
        return this.showThreadInfo;
    }

    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    public int getMethodOffset() {
        return this.methodOffset;
    }

    public LogAdapter getLogAdapter() {
        if(this.logAdapter == null) {
            this.logAdapter = new AndroidLogAdapter();
        }

        return this.logAdapter;
    }

    public void reset() {
        this.methodCount = 2;
        this.methodOffset = 0;
        this.showThreadInfo = true;
        this.logLevel = LogLevel.FULL;
    }
}