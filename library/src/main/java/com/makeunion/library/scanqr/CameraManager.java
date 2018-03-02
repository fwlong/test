package com.makeunion.library.scanqr;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * 描述: 相机管理
 */
public final class CameraManager {
    private static Context sContext;
    private final JpegCallback jpgCallback;
    private final ShutterCallback shutterCallback;
    private CameraManager cameraManager;

    static final int SDK_INT;

    static {
        int sdkInt;
        try {
            sdkInt = android.os.Build.VERSION.SDK_INT;
        } catch (NumberFormatException nfe) {
            sdkInt = 10000;
        }
        SDK_INT = sdkInt;
    }

    private ICameraConfig configManager;
    private Camera camera;
    private boolean initialized;
    private boolean previewing;
    private final boolean useOneShotPreviewCallback;
    private final PreviewCallback previewCallback;
    private final AutoFocusCallback autoFocusCallback;
    private Parameters parameter;

    public static CameraManager newInstance(Context context) {
        return new CameraManager(context);
    }


    private CameraManager(Context context) {
        this.configManager = new CameraConfigurationManager(context);

        useOneShotPreviewCallback = SDK_INT > 3;
        previewCallback = new PreviewCallback(configManager, useOneShotPreviewCallback);
        autoFocusCallback = new AutoFocusCallback();
        jpgCallback = new JpegCallback(this);
        shutterCallback = new ShutterCallback();
    }

    public void setConfigurationManager(ICameraConfig config) {
        this.configManager = config;
    }

    public void setPreviewCallback(ICameraConfig config) {
        this.configManager = config;
    }

    public void openDriver(SurfaceHolder holder) throws IOException {
        if (camera == null) {
            camera = Camera.open();
            if (camera == null) {
                throw new IOException();
            }
            camera.setPreviewDisplay(holder);

            if (!initialized) {
                initialized = true;
                configManager.initFromCameraParameters(camera);
            }
            configManager.setDesiredCameraParameters(camera);
            FlashlightManager.enableFlashlight();
        }
    }

    public Point getCameraResolution() {
        return configManager.getCameraResolution();
    }
    public Point getPictureResolution() {
        return configManager.getPictureResolution();
    }
    public void closeDriver() {
        if (camera != null) {
            FlashlightManager.disableFlashlight();
            camera.release();
            camera = null;
        }
    }

    public void startPreview() {
        if (camera != null && !previewing) {
            try {
                camera.startPreview();
                previewing = true;
            } catch (RuntimeException exception) {
                Log.e("CameraManager", "RuntimeException because of startPreview failed,give up !");
            }
        }
    }

    public void stopPreview() {
        if (camera != null && previewing) {
            if (!useOneShotPreviewCallback) {
                camera.setPreviewCallback(null);
            }
            camera.stopPreview();
            previewCallback.setHandler(null, 0);
            autoFocusCallback.setHandler(null, 0);
            previewing = false;
        }
    }

    public void requestPreviewFrame(Handler handler, int message) {
        if (camera != null && previewing) {
            previewCallback.setHandler(handler, message);
            if (useOneShotPreviewCallback) {
                camera.setOneShotPreviewCallback(previewCallback);
            } else {
                camera.setPreviewCallback(previewCallback);
            }
        }
    }

    public void requestAutoFocus(Handler handler, int message) {
        if (camera != null && previewing) {
            autoFocusCallback.setHandler(handler, message);
            try {
                camera.autoFocus(autoFocusCallback);
            } catch (RuntimeException exception) {
                Log.e("CameraManager", "RuntimeException because of autoFocus failed,give up !");
            }
        }
    }

    public void tankPicture(Handler handler, int message) {
        jpgCallback.setHandler(handler, message);
        if (camera != null && previewing) {
            camera.takePicture(shutterCallback, null, jpgCallback);
        }

    }

    public void openLight() {
        try {
            if (camera != null) {
                parameter = camera.getParameters();
                parameter.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameter);
            }
        } catch (RuntimeException exception) {
            Log.e("CameraManager", "RuntimeException because of getParameters failed,give up !");
        }
    }

    public void offLight() {
        try {
            if (camera != null) {
                parameter = camera.getParameters();
                parameter.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameter);
            }
        } catch (RuntimeException exception) {
            Log.e("CameraManager", "RuntimeException because of getParameters failed,give up !");
        }
    }
}
