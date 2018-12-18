package com.chengsheng.cala.htcm.utils;

import static com.chengsheng.cala.htcm.constant.GlobalConstant.ACCESS_TOKEN;
import static com.chengsheng.cala.htcm.constant.GlobalConstant.MOBILE;
import static com.chengsheng.cala.htcm.constant.GlobalConstant.TOKEN_TYPE;

/**
 * Author: 任和
 * CreateDate: 2018/12/18 11:24 AM
 * Description: 维护用户信息相关工具类
 */
public class UserUtil {

    /**
     * 设置用户token
     */
    public static void setAccessToken(String token) {
        PreferenceUtil.setPrefString(ACCESS_TOKEN, token);
    }

    /**
     * 获取用户token
     */
    public static String getAccessToken() {
        return PreferenceUtil.getPrefString(ACCESS_TOKEN, "");
    }

    /**
     * 设置tokenType
     */
    public static void setTokenType(String type) {
        PreferenceUtil.setPrefString(TOKEN_TYPE, type);
    }

    /**
     * 获取tokenType
     */
    public static String getTokenType() {
        return PreferenceUtil.getPrefString(TOKEN_TYPE, "");
    }

    /**
     * 设置用户登录的手机号
     */
    public static void setMobile(String mobile) {
        PreferenceUtil.setPrefString(MOBILE, mobile);
    }

    /**
     * 获取用户登录的手机号
     */
    public static String getMobile() {
        return PreferenceUtil.getPrefString(MOBILE, "");
    }

    /**
     * 用户是否登录
     */
    public static boolean isLogin() {
        return !getAccessToken().isEmpty();
    }

}
