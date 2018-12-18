package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class ChangePhoneActivity extends BaseActivity {

    private ImageView back;
    private TextView title;
    private Button changePhone;
    private TextView phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initView() {
        String phoneNum = getIntent().getStringExtra("USER_NUM");

        back = findViewById(R.id.title_header_change_phone).findViewById(R.id.back_login);
        title = findViewById(R.id.title_header_change_phone).findViewById(R.id.menu_bar_title);
        changePhone = findViewById(R.id.change_phone_buton);
        phone = findViewById(R.id.user_phone);

        title.setText("更换手机号");
        phone.setText("您的手机号为" + phoneNum);
        back.setOnClickListener(new View.OnClickListener() {
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
