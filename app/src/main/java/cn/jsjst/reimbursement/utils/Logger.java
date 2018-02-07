package cn.jsjst.reimbursement.utils;

import com.orhanobut.logger.AndroidLogAdapter;

import cn.jsjst.reimbursement.BuildConfig;

/**
 * Logger
 * log统一输出类
 **/
public class Logger {

    static {
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter());
    }

    /**
     * log的开关
     */
    private static boolean isDebug = BuildConfig.DEBUG;

    /**
     * log的输出tag
     */
    private static String TAG = "mike";


    /**
     * <setTAG>
     * 设置log的TAG
     **/
    public static void setTAG(String tAG) {
        TAG = tAG;
    }

    /**
     * <i>
     * 正常调试log输出
     *
     * @param msg msg log的内容
     **/
    public static void i(String msg) {
        if (isDebug) {
            com.orhanobut.logger.Logger.i(msg);
        }
    }

    /**
     * <e>
     * 异常log输出
     *
     * @param msg log的内容
     **/
    public static void e(String msg) {
        if (isDebug) {
            com.orhanobut.logger.Logger.e(msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            com.orhanobut.logger.Logger.d(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            com.orhanobut.logger.Logger.d(msg);
        }
    }

    /**
     * <w>
     */
    public static void w(String msg) {
        if (isDebug) {
            com.orhanobut.logger.Logger.w(msg);
        }
    }

    /**
     * <e>
     * 异常log输出
     *
     * @param msg log的内容
     * @param tr  异常信息
     **/
    public static void e(String msg, Throwable tr) {
        if (isDebug) {
            com.orhanobut.logger.Logger.e(tr, msg);
        }
    }

    /**
     * 打印json信息
     */
    public static void json(String msg) {
        if (isDebug) {
            com.orhanobut.logger.Logger.json(msg);
        }
    }

    /**
     * 打印xml信息
     */
    public static void xml(String msg) {
        if (isDebug) {
            com.orhanobut.logger.Logger.xml(msg);
        }
    }

    /**
     * 关闭log
     */
    public static void closeLogger() {
        isDebug = false;
    }
}
