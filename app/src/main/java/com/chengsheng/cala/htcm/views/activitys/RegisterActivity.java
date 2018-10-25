package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class RegisterActivity extends AppCompatActivity {

    private TextView backLoginButton;
    private TextView completeRegister;

    private boolean tempIsRegister = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        backLoginButton = (TextView) findViewById(R.id.back_login_button);
//        completeRegister = (TextView) findViewById(R.id.complete_register);

//        backLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        completeRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(tempIsRegister){
//                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        });
    }
}
