package com.chengsheng.cala.htcm.utils;

import android.content.Context;
import android.content.SharedPreferences;


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
}
