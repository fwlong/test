package com.makeunion.library.scanqr;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * <p>write the description
 *
 * @author wangshan
 * @version 2.0.0
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ScreenUtils {
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = Math.min(dm.heightPixels, dm.widthPixels);// 获取屏幕分辨率宽度
        return screenHeight;
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.widthPixels;// 获取屏幕分辨率宽度
        return screenHeight;
    }
}
