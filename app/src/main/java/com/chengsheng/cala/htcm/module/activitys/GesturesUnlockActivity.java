package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;

public class GesturesUnlockActivity extends BaseActivity {
    private TextView titleText;
    private ImageView noProtectIcon, startProtectIcon, inappProtectIcon;
    private ImageView noProtectMark, startProtectMark, inappProtectMark;
    private Button settingGesturesCodeButton;


    @Override
    public int getLayoutId() {
        return R.layout.activity_gestures_unlock;
    }

    @Override
    public void initView() {
        initViews();

        titleText.setText("手势解锁");

        updateIconStats(true, false, false);

        noProtectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIconStats(true, false, false);
            }
        });

        startProtectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIconStats(false, true, false);
            }
        });

        inappProtectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIconStats(false, false, true);
            }
        });
    }

    @Override
    public void getData() {

    }

    private void initViews() {
        titleText = findViewById(R.id.title_header_gesture_unlock).findViewById(R.id.menu_bar_title);
        noProtectIcon = findViewById(R.id.no_protect_icon);
        startProtectIcon = findViewById(R.id.start_protect_icon);
        inappProtectIcon = findViewById(R.id.inapp_protect_icon);
        noProtectMark = findViewById(R.id.no_protect_mark);
        startProtectMark = findViewById(R.id.start_protect_mark);
        inappProtectMark = findViewById(R.id.inapp_protect_mark);
        settingGesturesCodeButton = findViewById(R.id.setting_gestures_code_button);

    }

    private void updateIconStats(boolean a, boolean b, boolean c) {
        noProtectIcon.setSelected(a);
        noProtectMark.setSelected(a);
        startProtectIcon.setSelected(b);
        startProtectMark.setSelected(b);
        inappProtectIcon.setSelected(c);
        inappProtectMark.setSelected(c);
        if (a) {
            settingGesturesCodeButton.setVisibility(View.INVISIBLE);
        } else {
            settingGesturesCodeButton.setVisibility(View.VISIBLE);
        }

    }
}
