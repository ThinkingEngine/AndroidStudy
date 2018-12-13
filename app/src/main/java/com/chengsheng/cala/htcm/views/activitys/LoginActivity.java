package com.chengsheng.cala.htcm.views.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
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

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.LoginData;
import com.chengsheng.cala.htcm.model.services.ServiceSMSService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.network.NetworkStateCallback;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {
    private TextView registerTV;
    private TextView loginTV;
    private TextView retrieveTV;
    private EditText cellphoneEdittext, passwordEdittext;
    private Button deleteInput, previewIcon;
    private TextView loginTelService;

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
        loginTelService = findViewById(R.id.login_tel_service);

        deleteInput.setVisibility(View.INVISIBLE);

        app = HTCMApp.create(getApplicationContext());
        final ZLoadingDialog dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        dialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        dialog.setHintText("登录中....");
        dialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));

        MyRetrofit myRetrofit = MyRetrofit.createInstance();
        Retrofit retrofit = myRetrofit.createDefault();
        final NetService service = myRetrofit.create(retrofit, NetService.class);

        String registerStr = getIntent().getStringExtra("USER_NUMBER");
        cellphoneEdittext.setText(registerStr);


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
                        dialog.show();
                        service.login(HTCMApp.getClientId(),HTCMApp.getGrantType(), userNameInput,passwordInput, HTCMApp.getClientSecret(), HTCMApp.getScope())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableObserver<LoginData>() {
                                    @Override
                                    public void onNext(LoginData data) {
                                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        app.setUserName(userNameInput);
                                        app.setUserRegister(GlobalConstant.USER_STATE_REGISTER);
                                        app.setAccessToken(data.getAccess_token());
                                        app.setExpiresIn(data.getExpires_in());
                                        app.setTokenType(data.getToken_type());
                                        FuncUtils.putBoolean("REGISTER", true);
                                        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                                        dialog.cancel();
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                        Log.e("TAG","错误:"+e.toString());
                                        dialog.cancel();
                                        AlertDialog alertDialog;
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setTitle("登录失败");
                                        builder.setMessage("登录失败：账户或密码错误!");
                                        builder.setPositiveButton("确认", null);
                                        alertDialog = builder.create();
                                        alertDialog.show();

                                    }

                                    @Override
                                    public void onComplete() {
                                    }
                                });
                    }


                }

            }
        });

        loginTelService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("客服电话");
                builder.setMessage("您确定需要拨打客服电话!");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + loginTelService.getText().toString());
                        intent.setData(data);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("暂不", null);

                alertDialog = builder.create();
                alertDialog.show();
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
