package com.chengsheng.cala.htcm.utils;

import android.content.Context;

/**
 * Author: 任和
 * CreateDate: 2018/12/18 10:30 AM
 * Description: Toast提示工具类
 */
public class ToastUtil {

    public static void showShortToast(Context context, String content) {
        if (null != context && null != content && content.trim().length() != 0)
            ToastInstance.getInstance().showShortToast(context, content);
    }

    public static void showShortToast(Context context, int resId) {
        if (null != context && resId != -1)
            ToastInstance.getInstance().showShortToast(context, resId);
    }

    public static void showShortToast(Context context, int resId,
                                      Object... args) {
        if (null != context) {
            String content = context.getString(resId, args);
            ToastInstance.getInstance().showShortToast(context, content);
        }
    }

    public static void showLongToast(Context context, String content) {
        if (null != context && null != content && content.trim().length() != 0)
            ToastInstance.getInstance().showLongToast(context, content);
    }

    public static void showLongToast(Context context, int resId) {
        if (null != context && resId != -1)
            ToastInstance.getInstance().showLongToast(context, resId);
    }

    public static void showLongToast(Context context, int resId, Object... args) {
        if (null != context) {
            String content = context.getString(resId, args);
            ToastInstance.getInstance().showLongToast(context, content);
        }
    }
}
