package com.chengsheng.cala.htcm;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class HTCMApplication extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        Fresco.initialize(this);
    }
}
