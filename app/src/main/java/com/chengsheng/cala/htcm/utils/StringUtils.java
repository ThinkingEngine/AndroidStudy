package com.chengsheng.cala.htcm.utils;

/**
 * Author: 任和
 * CreateDate: 2018/12/25 11:27 AM
 * Description:
 */
public class StringUtils {

    /**
     * 每隔4位对字符串加空格
     */
    public static String addBlank(String replace) {
        if (replace.isEmpty() || replace.length() < 4) return replace;
        String regex = "(.{4})";
        replace = replace.replaceAll(regex, "$1 ");
        return replace;
    }
}
