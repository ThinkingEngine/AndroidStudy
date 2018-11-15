package com.chengsheng.cala.htcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chengsheng.cala.htcm.views.activitys.HomePageActivity;
import com.chengsheng.cala.htcm.views.activitys.LoginActivity;

public class LaunchActivity extends AppCompatActivity {

    private boolean tempJudgeCondition = false;
    private HTCMApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        app = HTCMApp.create(getApplicationContext());


    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Thread.sleep(1000);
            if(app.getRegisterState() == GlobalConstant.USER_STATE_UNREGISTER){
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this,HomePageActivity.class);
                startActivity(intent);
                finish();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
