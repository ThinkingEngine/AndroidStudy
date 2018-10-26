package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class SettingActivity extends AppCompatActivity {

    private TextView titleText;
    private ImageView intoSecuritySetting,intoCommonUse,intoShare,intoFeedback,intoAbout;
    private Button outLineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initViews();

        titleText.setText("设置");

        intoSecuritySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,SecuritySettingsActivity.class);
                startActivity(intent);
            }
        });

        intoCommonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,CommonUseActivity.class);
                startActivity(intent);
            }
        });

        intoFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,FeedbackActivity.class);
                startActivity(intent);
            }
        });

        intoAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
        titleText = (TextView) findViewById(R.id.title_header_setting).findViewById(R.id.menu_bar_title);
        intoSecuritySetting = (ImageView) findViewById(R.id.into_security_setting);
        intoCommonUse = (ImageView) findViewById(R.id.into_common_use);
        intoShare = (ImageView) findViewById(R.id.into_share);
        intoFeedback = (ImageView) findViewById(R.id.into_feedback);
        intoAbout = (ImageView) findViewById(R.id.into_about);

        outLineButton = (Button) findViewById(R.id.out_line_button);
    }
}
