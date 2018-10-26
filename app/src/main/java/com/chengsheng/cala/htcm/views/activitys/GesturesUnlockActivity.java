package com.chengsheng.cala.htcm.views.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class GesturesUnlockActivity extends AppCompatActivity {
    private TextView titleText;
    private ImageView noProtectIcon,startProtectIcon,inappProtectIcon;
    private ImageView noProtectMark,startProtectMark,inappProtectMark;
    private Button settingGesturesCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestures_unlock);

        initViews();

        titleText.setText("手势解锁");

        updateIconStats(true,false,false);

        noProtectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIconStats(true,false,false);
            }
        });

        startProtectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIconStats(false,true,false);
            }
        });

        inappProtectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIconStats(false,false,true);
            }
        });

    }

    private void initViews(){
        titleText = (TextView) findViewById(R.id.title_header_gesture_unlock).findViewById(R.id.menu_bar_title);
        noProtectIcon = (ImageView) findViewById(R.id.no_protect_icon);
        startProtectIcon = (ImageView) findViewById(R.id.start_protect_icon);
        inappProtectIcon = (ImageView) findViewById(R.id.inapp_protect_icon);
        noProtectMark = (ImageView) findViewById(R.id.no_protect_mark);
        startProtectMark = (ImageView) findViewById(R.id.start_protect_mark);
        inappProtectMark = (ImageView) findViewById(R.id.inapp_protect_mark);
        settingGesturesCodeButton = (Button) findViewById(R.id.setting_gestures_code_button);

    }

    private void updateIconStats(boolean a,boolean b,boolean c){
        noProtectIcon.setSelected(a);
        noProtectMark.setSelected(a);
        startProtectIcon.setSelected(b);
        startProtectMark.setSelected(b);
        inappProtectIcon.setSelected(c);
        inappProtectMark.setSelected(c);
        if(a){
            settingGesturesCodeButton.setVisibility(View.INVISIBLE);
        }else{
            settingGesturesCodeButton.setVisibility(View.VISIBLE);
        }

    }
}
