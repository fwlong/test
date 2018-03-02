package com.makeunion.library.scanqr;

import android.graphics.Point;
import android.hardware.Camera;

/**
 * Created by gaojun on 2016/2/17.
 */
public interface ICameraConfig {
    void initFromCameraParameters(Camera camera);

    void setDesiredCameraParameters(Camera camera);

    Point getCameraResolution();
     Point getPictureResolution();
}

