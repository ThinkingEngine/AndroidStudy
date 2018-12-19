package com.chengsheng.cala.htcm.module;

import android.os.Handler;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.module.home.MainActivity;

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
//        startActivity(new MainActivity());
        startActivity(new MainActivity());
        finish();
    }

    @Override
    public void getData() {

    }
}
