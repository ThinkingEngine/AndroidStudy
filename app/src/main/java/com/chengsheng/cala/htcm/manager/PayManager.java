package com.chengsheng.cala.htcm.manager;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.chengsheng.cala.htcm.utils.WeakRefHandler;

/**
 * Author: 任和
 * CreateDate: 2018/12/27 8:50 AM
 * Description: 封装支付功能
 */
public class PayManager {

    private static PayManager instance;

    private PayManager() {

    }

    public static PayManager getDefault() {
        if (instance == null) {
            synchronized (PayManager.class) {
                if (instance == null) {
                    instance = new PayManager();
                }
            }
        }
        return instance;
    }

    private IPayListener payListener;

    /**
     * 调用支付宝支付
     */
    public void callAlipay(Activity activity, String sign, IPayListener payListener) {
        this.payListener = payListener;
        Thread payThread;
        payThread = new Thread(() -> {
            PayTask payTask = new PayTask(activity);

            String result = payTask.pay(sign, true);
            Message msg = new Message();
            msg.what = 1;
            msg.obj = result;
            handler.sendMessage(msg);
        });
        payThread.start();
    }

    private Handler.Callback callback = msg -> {
        if (msg.what == 1) {
            String payResult = msg.obj.toString();
            if (!payResult.isEmpty() && payListener != null) {
                if (payResult.contains("resultStatus={9000}")) {
                    payListener.onSuccess();
                } else if (payResult.contains("resultStatus={6001}")) {
                    payListener.onCancel();
                } else {
                    payListener.onFail();
                }
            }
        }

        return true;
    };

    private Handler handler = new WeakRefHandler(callback);

}
