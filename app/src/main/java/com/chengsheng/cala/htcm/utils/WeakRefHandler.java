package com.chengsheng.cala.htcm.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Author: 任和
 * CreateDate: 2018/12/27 9:02 AM
 * Description: 实现弱引用的Handler，防止由于内部持有导致的内存泄露
 */
public class WeakRefHandler extends Handler {
    private WeakReference<Callback> mWeakReference;

    public WeakRefHandler(Callback callback) {
        mWeakReference = new WeakReference<>(callback);
    }

    public WeakRefHandler(Callback callback, Looper looper) {
        super(looper);
        mWeakReference = new WeakReference<>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mWeakReference != null && mWeakReference.get() != null) {
            Callback callback = mWeakReference.get();
            callback.handleMessage(msg);
        }
    }
}
