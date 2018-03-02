package com.makeunion.library.scanqr;


import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;


/**
 * 描述: 扫描消息转发
 */
public final class CaptureActivityHandler extends Handler {
    public static final int MSG_BASE = 0;
    public static final int MSG_AUTO_FOUCS = MSG_BASE + 1;
    public static final int MSG_RESTART_PREVIEW = MSG_BASE + 2;
    public static final int MSG_START_ALBUM = MSG_BASE + 4;
    public static final int MSG_DECODE_ALBUM = MSG_BASE + 5;
    public static final int MSG_DECODE_SUCCESS = MSG_BASE + 6;
    public static final int MSG_DECODE_FAIL = MSG_BASE + 7;
    public static final int MSG_DECODE_ALBUM_FAIL = MSG_BASE + 8;
    public static final int MSG_DECODE = MSG_BASE + 9;
    public static final int MSG_QUIT = MSG_BASE + 10;
    private final CameraManager mCameraManager;
    private final boolean hasCameraAutoFocusFeature;
    DecodeThread decodeThread = null;
    CaptureActivity activity = null;
    private State state;

    public void onDestroy() {
        if (activity != null) {
            this.activity = null;
        }
        decodeThread.onDestroy();
        removeCallbacksAndMessages(null);

    }

    private enum State {
        PREVIEW, SUCCESS, DONE
    }

    public CaptureActivityHandler(CaptureActivity activity) {
        this.activity = activity;
        hasCameraAutoFocusFeature = activity.getApplicationContext().getPackageManager().hasSystemFeature(


                PackageManager.FEATURE_CAMERA_AUTOFOCUS);
        mCameraManager = activity.getCameraManager();
    }

    public void startDecodeQrCode() {
        decodeThread = new DecodeThread(activity);
        decodeThread.start();
        state = State.SUCCESS;
        mCameraManager.startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {

        switch (message.what) {
            case MSG_AUTO_FOUCS:
                if (state == State.PREVIEW) {
                    if (hasCameraAutoFocusFeature){
                        mCameraManager.requestAutoFocus(this, MSG_AUTO_FOUCS);
                    }
                }
                break;
            case MSG_RESTART_PREVIEW:
                restartPreviewAndDecode();
                break;
            case MSG_START_ALBUM:
                Message msg = decodeThread.getHandler().obtainMessage(MSG_DECODE_ALBUM, message.arg1, message.arg2);
                msg.obj = message.obj;
                decodeThread.getHandler().sendMessage(msg);
                break;
            case MSG_DECODE_SUCCESS:
                state = State.SUCCESS;
                if (activity != null) {
                    activity.handleDecode((String) message.obj);
                }// 解析成功，回调
                break;
            case MSG_DECODE_ALBUM_FAIL:

                break;
            case MSG_DECODE_FAIL:
                state = State.PREVIEW;
                mCameraManager.requestPreviewFrame(decodeThread.getHandler(),
                        MSG_DECODE);
                break;
        }

    }

    public void quitSynchronously() {
        state = State.DONE;
        mCameraManager.stopPreview();
        removeMessages(MSG_DECODE_SUCCESS);
        removeMessages(MSG_DECODE_FAIL);
        removeMessages(MSG_DECODE);
        removeMessages(MSG_AUTO_FOUCS);
    }

    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            mCameraManager.requestPreviewFrame(decodeThread.getHandler(),
                    MSG_DECODE);
            if (hasCameraAutoFocusFeature){
                mCameraManager.requestAutoFocus(this, MSG_AUTO_FOUCS);
            }
        }
    }

}
