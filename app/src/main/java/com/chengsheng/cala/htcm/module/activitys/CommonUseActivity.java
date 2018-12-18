package com.chengsheng.cala.htcm.module.activitys;

import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class CommonUseActivity extends BaseActivity {
    private TextView titleText;


    @Override
    public int getLayoutId() {
        return R.layout.activity_common_use;
    }

    @Override
    public void initView() {
        titleText = (TextView) findViewById(R.id.title_header_common_use).findViewById(R.id.menu_bar_title);

        titleText.setText("通用");
    }

    @Override
    public void getData() {

    }
}
