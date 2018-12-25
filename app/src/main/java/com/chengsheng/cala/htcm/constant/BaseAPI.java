package com.chengsheng.cala.htcm.constant;

import com.chengsheng.cala.htcm.BuildConfig;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-19 14:36
 * Description:
 */
public class BaseAPI {

    /**
     * 账号相关基地址
     */
    private static final String DEV_BASE_URL = "http://account.zz-tech.com.cn:85/";
    private static final String LIVE_BASE_URL = "";

    /**
     * 业务基地址
     */
    private static final String DEV_SERVICE_URL = "http://192.168.1.119:9001/";
    private static final String LIVE_SERVICE_URL = "http://api.peis-mobile.zz-tech.com.cn:85/";

    /**
     * 账号api接口根地址
     */
    public static final String ACCOUNT_BASE_URL = BuildConfig.isDev ? DEV_BASE_URL : LIVE_BASE_URL;

    /**
     * 账号api接口根地址
     */
    public static final String SERVICE_BASE_URL = BuildConfig.isDev ? DEV_SERVICE_URL : LIVE_SERVICE_URL;//获取服务URL

}
