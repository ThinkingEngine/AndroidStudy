package com.chengsheng.cala.htcm.module.activitys;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class AboutActivity extends BaseActivity {
    private TextView menuBarTitle;
    private ImageView backLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        menuBarTitle = findViewById(R.id.title_header_about).findViewById(R.id.menu_bar_title);
        backLogin = findViewById(R.id.title_header_about).findViewById(R.id.back_login);

        menuBarTitle.setText("关于");
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getData() {

    }
}
