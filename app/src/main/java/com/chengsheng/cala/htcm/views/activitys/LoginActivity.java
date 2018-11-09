package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

public class LoginActivity extends AppCompatActivity {
    private TextView registerTV;
    private TextView loginTV;
    private TextView retrieveTV;
    private EditText cellphoneEdittext,passwordEdittext;
    private Button deleteInput,previewIcon;

    private boolean tempLogin = true;
    private boolean passVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerTV = findViewById(R.id.register_button);
        loginTV = findViewById(R.id.login_button);
        retrieveTV = findViewById(R.id.retrieve_pw_button);
        cellphoneEdittext = findViewById(R.id.cellphone_edittext);
        passwordEdittext = findViewById(R.id.password_edittext);
        deleteInput = findViewById(R.id.delete_input);
        previewIcon = findViewById(R.id.preview_icon);

        deleteInput.setVisibility(View.INVISIBLE);

        passwordEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        passwordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null){
                    deleteInput.setVisibility(View.VISIBLE);
                }

                if(s.length() == 0){
                    deleteInput.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        previewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!passVisible){
                    passVisible = true;
                    passwordEdittext.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    passVisible = false;
                    passwordEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        deleteInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordEdittext.setText("");
                deleteInput.setVisibility(View.INVISIBLE);
            }
        });



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
