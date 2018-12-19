package com.chengsheng.cala.htcm.constant;

import com.chengsheng.cala.htcm.BuildConfig;

/**
 * Author: 任和
 * CreateDate: 2018/12/18 1:41 PM
 * Description: 所有API接口地址
 */
public class API {

    private static final String DEV_BASE_URL = "http://account.zz-tech.com.cn:85/";
    private static final String LIVE_BASE_URL = "";

    /**
     * api接口根地址
     */
    private static final String API_BASE_URL = BuildConfig.isDev ? DEV_BASE_URL : LIVE_BASE_URL;

    /* 用户信息 */
    public static final String USER_INFO_URL = API_BASE_URL + "user/account-infos";
}
