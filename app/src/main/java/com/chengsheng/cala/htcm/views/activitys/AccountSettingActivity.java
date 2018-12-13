package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.childmodela.UserInfo;
import com.facebook.drawee.view.SimpleDraweeView;

public class AccountSettingActivity extends BaseActivity {

    private TextView titleHeader;
    private ImageView back;
    private SimpleDraweeView userHeaderIcon;
    private TextView needModNum;
    private TextView needModNickname;
    private TextView needModLoginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        Bundle bundle = getIntent().getExtras();
        final UserInfo userInfo = (UserInfo) bundle.getSerializable("USER_INFO");

        userHeaderIcon = findViewById(R.id.user_header_icon);
        needModNum = findViewById(R.id.need_mod_num);
        needModNickname = findViewById(R.id.need_mod_nickname);
        needModLoginPassword = findViewById(R.id.need_mod_login_password);


        userHeaderIcon.setImageURI(userInfo.getAvatar_url());
        needModNickname.setText(userInfo.getNickname());
        needModNum.setText(userInfo.getPhone_number());
        back = findViewById(R.id.title_header_account_setting).findViewById(R.id.back_login);
        titleHeader = findViewById(R.id.title_header_account_setting).findViewById(R.id.menu_bar_title);
        titleHeader.setText("账户设置");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userHeaderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingActivity.this,HeaderIconActivity.class);
                intent.putExtra("HEADER_URI",userInfo.getAvatar_url());
                startActivity(intent);
            }
        });

        needModNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingActivity.this,ChangePhoneActivity.class);
                intent.putExtra("USER_NUM",userInfo.getPhone_number());
                startActivity(intent);
            }
        });
    }
}
