package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class LoginActivity extends AppCompatActivity {
    private TextView registerTV;
    private TextView loginTV;
    private TextView retrieveTV;

    private boolean tempLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerTV = (TextView) findViewById(R.id.register_button);
        loginTV = (TextView) findViewById(R.id.login_button);
        retrieveTV = (TextView) findViewById(R.id.retrieve_pw_button);
//
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        retrieveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RetrievePWActivity.class);
                startActivity(intent);
            }
        });

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tempLogin){
                    Intent intent = new Intent(LoginActivity.this,HomePageActivity.class);
                    startActivity(intent);
                    //登录完成后 注销登录页。
                    finish();
                }

            }
        });
    }
}
