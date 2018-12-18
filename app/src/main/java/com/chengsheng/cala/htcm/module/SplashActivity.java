package com.chengsheng.cala.htcm.module;

import android.content.Intent;
import android.os.Handler;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.account.LoginActivity;
import com.chengsheng.cala.htcm.module.activitys.MainActivity;

/**
 * Author: 任和
 * CreateDate: 2018/12/17 9:25 PM
 * Description: APP启动页
 */
public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    public void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                start();

            }
        }, 2000);
    }

    private void start() {
        HTCMApp app = HTCMApp.create(getApplicationContext());

        if (app.getRegisterState() == GlobalConstant.USER_STATE_UNREGISTER) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void getData() {

    }
}
