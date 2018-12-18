package com.chengsheng.cala.htcm.utils;

import com.chengsheng.cala.htcm.BuildConfig;
import com.orhanobut.logger.Logger;


/**
 * Author: 任和
 * CreateDate: 2018/12/18 10:30 AM
 * Description: Log日志打印工具类
 */
public class LogUtil {
    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.i(msg);
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.d(msg);
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.e(msg);
        }
    }

    public static void json(String jsonStr) {
        if (BuildConfig.DEBUG) {
            Logger.json(jsonStr);
        }
    }
}
