package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class AccountSettingActivity extends AppCompatActivity {

    private TextView titleHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        titleHeader = (TextView) findViewById(R.id.title_header_account_setting).findViewById(R.id.menu_bar_title);
        titleHeader.setText("账户设置");
    }
}
