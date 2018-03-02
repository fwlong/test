package com.makeunion.library.logger;

/**
 * Created by renjialiang on 2017/7/14.
 */

public interface LogAdapter {
    void d(String var1, String var2);

    void e(String var1, String var2);

    void w(String var1, String var2);

    void i(String var1, String var2);

    void v(String var1, String var2);

    void wtf(String var1, String var2);
}