package com.makeunion.library.scanqr;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;

/**
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

    CaptureActivity activity;
    private DecodeHandler handler;
    private final CountDownLatch handlerInitLatch;

    DecodeThread(CaptureActivity activity) {
        super("DecodeThread");
        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);
    }

    Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity, Looper.myLooper());
        handlerInitLatch.countDown();
        Looper.loop();
    }

    public void onDestroy() {
        handler.onDestroy();
        if (activity != null) {
            activity = null;
        }
    }
}
