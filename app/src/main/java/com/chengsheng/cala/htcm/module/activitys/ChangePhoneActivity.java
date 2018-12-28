package com.chengsheng.cala.htcm.module.activitys;

import android.widget.Button;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.utils.StringUtils;

/**
 * Author: 任和
 * CreateDate: 2018/12/28 9:54 AM
 * Description: 修改手机号
 */
public class ChangePhoneActivity extends BaseActivity {

    private Button changePhone;
    private TextView phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initView() {
        String phoneNum = getIntent().getStringExtra("mobile");
        changePhone = findViewById(R.id.change_phone_buton);
        phone = findViewById(R.id.user_phone);
        phone.setText("您的手机号为" + StringUtils.formatMobile(phoneNum));
    }

    @Override
    public void getData() {

    }
}
