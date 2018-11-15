package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.SMSVerificationResult;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.FuncUtils;

import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    private ImageView back;
    private TextView login;
    private EditText getUserNum, getCodeUser, passwordInput, isOkPasswd;
    private ImageView deleteInput;
    private Button getCodeButton, comingRegisterButton;
    private TextView userProtocol, serviceNum;

    private String userNum = "";
    private String verificationCodeId = "";

    private boolean tempIsRegister = true;
    private MyRetrofit myRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final HTCMApp app = HTCMApp.create(getApplicationContext());
        myRetrofit = MyRetrofit.createInstance();
        final Retrofit retrofitURL = myRetrofit.createURL(GlobalConstant.TEST_URL);
        initViews();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

        //获取手机验证码按钮。
        getCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String number = getUserNum.getText().toString();
                if (FuncUtils.isMobileNO(number)) {
                    NetService service = retrofitURL.create(NetService.class);
                    service.getSMSVerification(getUserNum.getText().toString())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<SMSVerificationResult>() {
                                @Override
                                public void onNext(SMSVerificationResult s) {
                                    userNum = number;
                                    verificationCodeId = s.getCode_id();
                                    Toast.makeText(RegisterActivity.this, "请求发送成功，请等待短信消息", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //立即注册按钮。
        comingRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserNum.getText().toString().equals("") || !FuncUtils.isMobileNO(getUserNum.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
                } else if (getCodeUser.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "验证码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (passwordInput.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (isOkPasswd.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "请确认你的密码!", Toast.LENGTH_SHORT).show();
                } else if (!passwordInput.getText().toString().equals(isOkPasswd.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致!", Toast.LENGTH_SHORT).show();
                } else if(verificationCodeId.equals("") || !userNum.equals(getUserNum.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "该号码尚未验证，请先取得验证码!", Toast.LENGTH_SHORT).show();
                }else {
                    NetService service = retrofitURL.create(NetService.class);
                    Log.e("TEST", userNum + " " + passwordInput.getText().toString().trim() + " " + getCodeUser.getText().toString() + " " + HTCMApp.getClientId());
                    service.commitRegistrInfo(getUserNum.getText().toString(), passwordInput.getText().toString().trim(), verificationCodeId, getCodeUser.getText().toString(), HTCMApp.getClientId())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<ResponseBody>() {
                                @Override
                                public void onNext(ResponseBody o) {
                                    Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("USER_NUMBER", getUserNum.getText().toString());
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(RegisterActivity.this, "onError 结果:" + e, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });

                }
            }
        });

    }

    private void initViews() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
