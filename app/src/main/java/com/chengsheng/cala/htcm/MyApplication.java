package com.chengsheng.cala.htcm;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context myContext;


    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;
    }
}
