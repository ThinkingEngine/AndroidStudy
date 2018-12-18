package com.chengsheng.cala.htcm.module.activitys;

import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class FAQActivity extends BaseActivity {
    private TextView titleText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_faq;
    }

    @Override
    public void initView() {
        titleText = (TextView) findViewById(R.id.title_header_faq).findViewById(R.id.menu_bar_title);
        titleText.setText("常见问题");
    }

    @Override
    public void getData() {

    }
}
