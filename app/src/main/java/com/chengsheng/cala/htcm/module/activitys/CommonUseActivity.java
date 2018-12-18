package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class CommonUseActivity extends BaseActivity {
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_use);

        titleText = (TextView) findViewById(R.id.title_header_common_use).findViewById(R.id.menu_bar_title);

        titleText.setText("通用");
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }
}
