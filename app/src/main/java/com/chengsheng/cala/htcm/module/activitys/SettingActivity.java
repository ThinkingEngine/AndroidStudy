package com.chengsheng.cala.htcm.module.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.module.account.LoginActivity;
import com.chengsheng.cala.htcm.utils.PreferenceUtil;
import com.chengsheng.cala.htcm.utils.UserUtil;

//设置页面
public class SettingActivity extends BaseActivity {

    private TextView titleText;
    private RelativeLayout intoSecuritySetting, intoCommonUse, intoShare, intoFeedback, intoAbout;
    private Button outLineButton;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {

        initViews();

        titleText.setText("设置");

        intoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this,EstimateOrderActivity.class);
//                startActivity(intent);
            }
        });

        intoSecuritySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, SecuritySettingsActivity.class);
                startActivity(intent);
            }
        });

        intoCommonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, CommonUseActivity.class);
                startActivity(intent);
            }
        });

        intoFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        intoAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        outLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogOutDialog();
            }
        });
    }

    @Override
    public void getData() {

    }

    private void initViews() {
        titleText = findViewById(R.id.title_header_setting).findViewById(R.id.menu_bar_title);
        intoSecuritySetting = findViewById(R.id.relativeLayout2);
        intoCommonUse = findViewById(R.id.into_common_use);
        intoShare = findViewById(R.id.into_share);
        intoFeedback = findViewById(R.id.into_feedback);
        intoAbout = findViewById(R.id.into_about);
        outLineButton = findViewById(R.id.out_line_button);

        outLineButton.setVisibility(UserUtil.isLogin() ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 退出登录提示
     */
    private void showLogOutDialog() {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("退出提示");
        builder.setMessage("是否退出应用?");
        builder.setNegativeButton("暂不", null);
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PreferenceUtil.clear();
                outLineButton.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoginActivity.start(SettingActivity.this);
                        finish();
                    }
                }, 300);
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
