package com.chengsheng.cala.htcm.module.activitys;

import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class MyDevicesActivity extends BaseActivity {

    private TextView titleText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_devices;
    }

    @Override
    public void initView() {
        titleText = (TextView) findViewById(R.id.title_header_my_devices).findViewById(R.id.menu_bar_title);
        titleText.setText("我的设备");
    }

    @Override
    public void getData() {

    }
}
