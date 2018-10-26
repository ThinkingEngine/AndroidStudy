package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class SecuritySettingsActivity extends AppCompatActivity {
    private TextView securitySettings;
    private ImageView intoSettingGesturesUnlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_settings);

        securitySettings = (TextView) findViewById(R.id.title_header_security_settings).findViewById(R.id.menu_bar_title);
        intoSettingGesturesUnlock = (ImageView) findViewById(R.id.into_setting_gestures_unlock);

        securitySettings.setText("安全设置");

        intoSettingGesturesUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecuritySettingsActivity.this,GesturesUnlockActivity.class);
                startActivity(intent);
            }
        });
    }
}
