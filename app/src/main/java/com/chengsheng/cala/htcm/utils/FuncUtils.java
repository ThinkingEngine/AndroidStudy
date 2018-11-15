package com.chengsheng.cala.htcm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import static com.chengsheng.cala.htcm.MyApplication.myContext;

public class FuncUtils {


    private FuncUtils(){
        throw new UnsupportedOperationException("禁止实例化该类!");
    }



    public static SharedPreferences putString(String key,String value){
        myContext.getSharedPreferences("config",Context.MODE_PRIVATE)
        .edit()
        .putString(key,value)
        .commit();

        return myContext.getSharedPreferences("config",myContext.MODE_PRIVATE);
    }

    public static SharedPreferences putBoolean(String key,boolean value){
        myContext.getSharedPreferences("config",myContext.MODE_PRIVATE)
                .edit()
                .putBoolean(key,value)
                .commit();

        return myContext.getSharedPreferences("config",myContext.MODE_PRIVATE);
    }

    public static String getString(String key,String defaultVal){
        String result = myContext.getSharedPreferences("config",myContext.MODE_PRIVATE)
                .getString(key.trim(),defaultVal.trim());
        return result;
    }

    public static boolean getBoolean(String key,boolean defaultValue){
        return myContext.getSharedPreferences("config",myContext.MODE_PRIVATE).getBoolean(key,defaultValue);
    }

    public static SharedPreferences clear(){
        SharedPreferences sp = myContext.getSharedPreferences("config",myContext.MODE_PRIVATE);
        sp.edit().clear().commit();

        return  myContext.getSharedPreferences("config",myContext.MODE_PRIVATE);
    }

    public static int px2dip(Context context,int px){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px/scale + 0.5f);
    }

    public static int dip2px(int dp){
        final float density = myContext.getResources().getDisplayMetrics().density;

        return (int) (dp*density+0.5);
    }

    public static boolean isMobileNO(String mobileNum){
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */

        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNum))
            return false;
        else
            return mobileNum.matches(telRegex);
    }
}
