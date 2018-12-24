package com.chengsheng.cala.htcm.utils;

import static com.chengsheng.cala.htcm.constant.GlobalConstant.ACCESS_TOKEN;
import static com.chengsheng.cala.htcm.constant.GlobalConstant.TOKEN_TYPE;

/**
 * Author: 任和
 * CreateDate: 2018/12/24 5:08 PM
 * Description:
 */
public class CleanUtil {

    /**
     * 清除所有账户信息(登录手机号不清除，要默认填写在登录页输入框)
     */
    public static void cleanAllLoginInfo() {
        PreferenceUtil.remove(ACCESS_TOKEN);
        PreferenceUtil.remove(TOKEN_TYPE);
    }
}
