package com.chengsheng.cala.htcm.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.LoginData;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.network.NetworkStateCallback;
import com.chengsheng.cala.htcm.utils.FuncUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private TextView registerTV;
    private TextView loginTV;
    private TextView retrieveTV;
    private EditText cellphoneEdittext, passwordEdittext;
    private Button deleteInput, previewIcon;

    private boolean tempLogin = true;
    private boolean passVisible = false;

    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;

    private String userNameInput;
    private String passwordInput;

    private HTCMApp app;

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

        app = HTCMApp.create(getApplicationContext());
        MyRetrofit myRetrofit = MyRetrofit.createInstance();
        Retrofit retrofit = myRetrofit.createDefault();
        final NetService service = myRetrofit.create(retrofit,NetService.class);

        //监听网络状态。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new NetworkStateCallback(this));
            }

        }


        //密码框逻辑。
        passwordEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    deleteInput.setVisibility(View.VISIBLE);
                }

                if (s.length() == 0) {
                    deleteInput.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //隐藏密码按钮。
        previewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!passVisible) {
                    passVisible = true;
                    passwordEdittext.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    passVisible = false;
                    passwordEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        //清空密码框按钮。
        deleteInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordEdittext.setText("");
                deleteInput.setVisibility(View.INVISIBLE);
            }
        });


        //注册功能按钮。
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //忘记密码按钮。
        retrieveTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RetrievePWActivity.class);
                startActivity(intent);
            }
        });

        //登录按钮。
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempLogin) {
                    userNameInput = cellphoneEdittext.getText().toString();
                    passwordInput = passwordEdittext.getText().toString();
                    if (userNameInput.equals("") || passwordInput.equals("")) {
                        Toast.makeText(LoginActivity.this, "请输入电话号码和密码！", Toast.LENGTH_SHORT).show();
                    } else {
                        service.login(HTCMApp.getClientId(),
                                HTCMApp.getGrantType(), userNameInput,
                                passwordInput, HTCMApp.getClientSecret(), HTCMApp.getScope())
                                .subscribeOn(Schedulers.newThread()).
                                observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableObserver<LoginData>() {
                                    @Override
                                    public void onNext(LoginData data) {
                                        Log.e("Cai", "onNext");
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        app.setUserName(userNameInput);
                                        app.setUserRegister(GlobalConstant.USER_STATE_REGISTER);
                                        app.setAccessToken(data.getAccess_token());
                                        app.setExpiresIn(data.getExpires_in());
                                        app.setTokenType(data.getToken_type());
                                        FuncUtils.putBoolean("REGISTER",true);
                                        Log.e("TEST",data.toString());
                                        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                        startActivity(intent);
                                        //登录完成后 注销登录页。
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("Cai", "onError" + e);
                                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.e("Cai", "onComplete");
                                    }
                                });
                    }


                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        String userNum = getIntent().getStringExtra("USER_NUMBER");
        cellphoneEdittext.setText(userNum);
    }
}
