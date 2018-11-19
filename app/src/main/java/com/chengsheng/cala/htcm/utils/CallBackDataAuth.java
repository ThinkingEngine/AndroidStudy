package com.chengsheng.cala.htcm.utils;


import java.util.Map;

public class CallBackDataAuth {

    private static AuthStateCallBack mAuthStateCallBack;

    public static void setAuthStateCallBack(AuthStateCallBack authStateCallBack){
        mAuthStateCallBack = authStateCallBack;
    }

    public static void doAuthStateCallBack(Map<String,String> result){
        mAuthStateCallBack.authResult(result);
    }
}
