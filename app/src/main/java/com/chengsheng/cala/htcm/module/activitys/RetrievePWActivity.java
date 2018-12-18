package com.chengsheng.cala.htcm.module.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.module.account.LoginActivity;
import com.chengsheng.cala.htcm.module.fragments.SMSCodeFragment;
import com.chengsheng.cala.htcm.module.fragments.SMSCodeFragment.OnSMSCodeFragmentInteractionListener;
import com.chengsheng.cala.htcm.module.fragments.SetNewPWFragment;

public class RetrievePWActivity extends BaseActivity implements SetNewPWFragment.OnSetNewPWFFragmentInteractionListener
        , OnSMSCodeFragmentInteractionListener {
    private SMSCodeFragment smsCodeFragment;
    private SetNewPWFragment setNewPWFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pw);

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
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }

    @Override
    public void onSMSCodeFragmentInteraction(Bundle bundle) {
        String EVENT = bundle.getString("EVENT");
        String SOURCE = bundle.getString("SOURCE");
        if (EVENT.equals("click") && SOURCE.equals("backParent")) {
            Intent intent = new Intent(RetrievePWActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if (EVENT.equals("click") && SOURCE.equals("next")) {

            String phone = bundle.getString("V_PHONE");
            String vID = bundle.getString("V_ID");
            String code = bundle.getString("CODE");
            if (fragmentManager != null) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Log.e("TAG","VID+CODE::"+vID+"."+code);
                SetNewPWFragment setNewPWFragment = SetNewPWFragment.newInstance(phone,vID+"/"+code);
                ft.replace(R.id.retrieve_container, setNewPWFragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onSetNewPWFFragmentInteraction(Bundle bundle) {

        String EVENT = bundle.getString("EVENT");
        String SOURCE = bundle.getString("SOURCE");

        if (EVENT.equals("click") && SOURCE.equals("back")) {
            if (fragmentManager != null) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.retrieve_container, smsCodeFragment);
                ft.commit();
            }
        }
        if (EVENT.equals("click") && SOURCE.equals("complete")) {
            Intent intent = new Intent(RetrievePWActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("消息");
        alert.setMessage("退出当前页面会丢失所以修改信息！");
        alert.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RetrievePWActivity.this, "继续进行", Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();
    }
}
