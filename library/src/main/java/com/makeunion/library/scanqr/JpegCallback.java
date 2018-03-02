package com.makeunion.library.scanqr;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by gaojun on 2016/2/18.
 */
public class JpegCallback implements Camera.PictureCallback {
    private static final String TAG = PreviewCallback.class.getSimpleName();
    private final CameraManager cameraManager
            ;
    private Handler jpegHandler;
    private int jpegMessage;

    public JpegCallback(CameraManager cameraManager) {
        this.cameraManager =cameraManager;
    }

    void setHandler(Handler previewHandler, int previewMessage) {
        this.jpegHandler = previewHandler;
        this.jpegMessage = previewMessage;
    }
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        if (jpegHandler != null && jpegMessage != 0) {
            Message message = jpegHandler.obtainMessage(jpegMessage, data);
            message.sendToTarget();
            jpegHandler = null;
            cameraManager.stopPreview();
        } else {
            Log.d(TAG, "Got jpeg callback, but no handler for it");
        }
    }
}
