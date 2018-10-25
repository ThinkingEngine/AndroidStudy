package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.views.fragments.SMSCodeFragment;
import com.chengsheng.cala.htcm.views.fragments.SMSCodeFragment.OnSMSCodeFragmentInteractionListener;
import com.chengsheng.cala.htcm.views.fragments.SetNewPWFragment;

public class RetrievePWActivity extends AppCompatActivity implements SetNewPWFragment.OnSetNewPWFFragmentInteractionListener,OnSMSCodeFragmentInteractionListener {
    private SMSCodeFragment smsCodeFragment;
    private SetNewPWFragment setNewPWFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pw);

        smsCodeFragment = SMSCodeFragment.newInstance("","");
        setNewPWFragment = SetNewPWFragment.newInstance("","");

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if(smsCodeFragment != null){
            fragmentTransaction.add(R.id.retrieve_container,smsCodeFragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onSMSCodeFragmentInteraction(Bundle bundle) {
        String EVENT = bundle.getString("EVENT");
        String SOURCE = bundle.getString("SOURCE");
        if(EVENT.equals("click")&&SOURCE.equals("backParent")){
            Intent intent = new Intent(RetrievePWActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        if(EVENT.equals("click")&&SOURCE.equals("next")){
            if(fragmentManager != null){
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.retrieve_container,setNewPWFragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onSetNewPWFFragmentInteraction(Bundle bundle) {

        String EVENT = bundle.getString("EVENT");
        String SOURCE = bundle.getString("SOURCE");

        if(EVENT.equals("click") && SOURCE.equals("back")){
            if(fragmentManager != null){
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.retrieve_container,smsCodeFragment);
                ft.commit();
            }
        }
        if(EVENT.equals("click") && SOURCE.equals("complete")){
            Intent intent = new Intent(RetrievePWActivity.this,LoginActivity.class);
            startActivity(intent);
        }

    }
}
