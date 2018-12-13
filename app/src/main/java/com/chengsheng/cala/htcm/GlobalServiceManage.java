package com.chengsheng.cala.htcm;


import android.content.Context;
import android.support.annotation.NonNull;

public class GlobalServiceManage {

    private static volatile GlobalServiceManage serviceManage;

    private GlobalServiceManage(@NonNull Context context){
        if(serviceManage != null){
            throw new RuntimeException("服务管理器初始化异常");
        }
    }

    public static GlobalServiceManage create(@NonNull Context context){
        if(null == serviceManage){
            synchronized (GlobalServiceManage.class){
                if(null == serviceManage){
                    serviceManage = new GlobalServiceManage(context);
                }
            }
        }
        return serviceManage;
    }


    //waitting new services;



}
