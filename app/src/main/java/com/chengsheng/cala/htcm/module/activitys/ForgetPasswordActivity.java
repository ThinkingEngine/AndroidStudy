package com.chengsheng.cala.htcm.module.activitys;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.module.fragments.SMSCodeFragment;
import com.chengsheng.cala.htcm.module.fragments.SetNewPWFragment;

/**
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {
    private SMSCodeFragment smsCodeFragment;
    private SetNewPWFragment setNewPWFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public int getLayoutId() {
        return R.layout.activity_retrieve_pw;
    }

    @Override
    public void initView() {
        smsCodeFragment = SMSCodeFragment.newInstance("", "");
        setNewPWFragment = SetNewPWFragment.newInstance("", "");

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (smsCodeFragment != null) {
            fragmentTransaction.add(R.id.retrieve_container, smsCodeFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void getData() {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("消息");
        alert.setMessage("退出当前页面会丢失所以修改信息");
        alert.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();
    }
}
