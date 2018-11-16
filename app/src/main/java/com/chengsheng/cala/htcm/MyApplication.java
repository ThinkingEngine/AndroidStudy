package com.chengsheng.cala.htcm;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {

    public static Context myContext;


    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;

        Fresco.initialize(this);
    }
}
