package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView back;
    private TextView login;
    private EditText getUserNum,getCodeUser,passwordInput,isOkPasswd;
    private ImageView deleteInput;
    private Button getCodeButton,comingRegisterButton;
    private TextView userProtocol,serviceNum;

    private boolean tempIsRegister = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

       deleteInput.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getUserNum.setText("");
           }
       });

    }

    private void initViews(){

        back = findViewById(R.id.include).findViewById(R.id.back_login);
        login = findViewById(R.id.include).findViewById(R.id.into_login);
        getCodeUser = findViewById(R.id.get_code_user);
        getUserNum = findViewById(R.id.get_user_num);
        passwordInput = findViewById(R.id.password_input);
        isOkPasswd = findViewById(R.id.is_ok_passwd);
        deleteInput = findViewById(R.id.clear_number);
        getCodeButton = findViewById(R.id.get_code_button);
        comingRegisterButton = findViewById(R.id.coming_register_button);
        userProtocol = findViewById(R.id.user_protocol);
        serviceNum = findViewById(R.id.service_num);

    }
}
