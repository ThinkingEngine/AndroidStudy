package com.chengsheng.cala.htcm.module.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.SMSVerificationResult;
import com.chengsheng.cala.htcm.protocol.childmodela.RegisterError;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.google.gson.Gson;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class RegisterActivity extends BaseActivity {

    private ImageView back;
    private TextView login;
    private EditText getUserNum, getCodeUser, passwordInput, isOkPasswd;
    private ImageView deleteInput;
    private Button getCodeButton, comingRegisterButton;
    private TextView userProtocol, serviceNum;

    private String userNum = "";
    private String verificationCodeId = "";

    private Retrofit retrofit;
    private ZLoadingDialog loadingDialog;


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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintText("加载中...");


//        myRetrofit = MyRetrofit.createInstance();
//        final Retrofit retrofitURL = myRetrofit.createURL(GlobalConstant.TEST_URL);
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
                    getPhoneCode(number);
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }

            }
        });

        serviceNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("呼叫客服");
                builder.setMessage("您确认拨打客服电话？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + serviceNum.getText().toString());
                        intent.setData(data);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("暂不", null);
                alertDialog = builder.create();
                alertDialog.show();
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
                } else if (verificationCodeId.equals("") || !userNum.equals(getUserNum.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "该号码尚未验证，请先取得验证码!", Toast.LENGTH_SHORT).show();
                } else {
                    registerNewUser();
                }
            }
        });
    }

    @Override
    public void getData() {

    }

    private void getPhoneCode(final String number) {

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
        }

        loadingDialog.show();
        NetService service = retrofit.create(NetService.class);
        service.getSMSVerification(getUserNum.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SMSVerificationResult>() {
                    @Override
                    public void onNext(SMSVerificationResult s) {
                        userNum = number;
                        verificationCodeId = s.getCode_id();
                        loadingDialog.cancel();
                        Toast.makeText(RegisterActivity.this, "请求发送成功，请等待短信消息", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });
    }

    private void registerNewUser() {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
        }

        loadingDialog.show();
        NetService service = retrofit.create(NetService.class);
        service.commitRegistrInfo(getUserNum.getText().toString(), passwordInput.getText().toString().trim(), verificationCodeId, getCodeUser.getText().toString(),
                GlobalConstant.clientId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody o) {
                        loadingDialog.cancel();
                        Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("USER_NUMBER", getUserNum.getText().toString());
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            loadingDialog.cancel();
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Gson gson = new Gson();
                            try {
                                RegisterError error = gson.fromJson(body.string(), RegisterError.class);
                                showDialog(error);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        Toast.makeText(RegisterActivity.this, "onError 结果:" + e, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void showDialog(RegisterError error) {
        final AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(error.getError());
        builder.setMessage(error.getMessage());
        builder.setPositiveButton("请重新更改您的注册信息!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }
}
