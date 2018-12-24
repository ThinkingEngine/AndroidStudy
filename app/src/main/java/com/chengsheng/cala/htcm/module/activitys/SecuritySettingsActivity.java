package com.chengsheng.cala.htcm.module.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class SecuritySettingsActivity extends BaseActivity {
    private TextView securitySettings;
    private ImageView intoSettingGesturesUnlock;


    @Override
    public int getLayoutId() {
        return R.layout.activity_security_settings;
    }

    @Override
    public void initView() {
        securitySettings = findViewById(R.id.title_header_security_settings).findViewById(R.id.menu_bar_title);
        intoSettingGesturesUnlock = findViewById(R.id.into_setting_gestures_unlock);

        securitySettings.setText("安全设置");

        intoSettingGesturesUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecuritySettingsActivity.this,GesturesUnlockActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getData() {

    }
}
