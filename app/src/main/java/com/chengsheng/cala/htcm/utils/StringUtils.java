package com.chengsheng.cala.htcm.utils;

import android.widget.TextView;

/**
 * Author: 任和
 * CreateDate: 2018/12/25 11:27 AM
 * Description:
 */
public class StringUtils {

    /**
     * 获取输入字符
     */
    public static String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    /**
     * 每隔4位对字符串加空格
     */
    public static String addBlank(String replace) {
        if (replace != null) {
            if (replace.isEmpty() || replace.length() < 4) return replace;
            String regex = "(.{4})";
            replace = replace.replaceAll(regex, "$1 ");
            return replace;
        } else {
            return "";
        }
    }
}
