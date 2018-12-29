package com.chengsheng.cala.htcm.module.user.account;

import android.widget.Button;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.utils.StringUtils;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/**
 * Author: 任和
 * CreateDate: 2018/12/28 9:54 AM
 * Description: 修改手机号
 */
public class ChangePhoneActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initView() {
        String mobile = getIntent().getStringExtra("mobile");
        Button btnChangeMobile = findViewById(R.id.change_phone_buton);
        TextView tvMobile = findViewById(R.id.user_phone);
        tvMobile.setText("您的手机号为" + StringUtils.formatMobile(mobile));
        //更换手机号
        btnChangeMobile.setOnClickListener(view ->
                startActivity(new PwdVerificationActivity()));
    }

    @Override
    public void getData() {

    }

    @Subscriber(mode = ThreadMode.MAIN, tag = GlobalConstant.BIND_NEW_MOBILE_SUCCESS)
    public void changeMobileSuc(Object event) {
        finish();
    }
}
