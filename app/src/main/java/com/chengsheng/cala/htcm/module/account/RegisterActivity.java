package com.chengsheng.cala.htcm.module.account;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.API;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.WebActivity;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.SMSVerificationResult;
import com.chengsheng.cala.htcm.protocol.childmodela.RegisterError;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.utils.TimeUtilKt;
import com.chengsheng.cala.htcm.utils.UserUtil;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class RegisterActivity extends BaseActivity {

    private EditText getUserNum, getCodeUser, passwordInput, isOkPasswd;
    private ImageView deleteInput;
    private Button getCodeButton, comingRegisterButton;
    private TextView serviceNum;

    private String userNum = "";
    private String verificationCodeId = "";

    private Retrofit retrofit;

    private void initViews() {
        getCodeUser = findViewById(R.id.get_code_user);
        getUserNum = findViewById(R.id.get_user_num);
        passwordInput = findViewById(R.id.password_input);
        isOkPasswd = findViewById(R.id.is_ok_passwd);
        deleteInput = findViewById(R.id.clear_number);
        getCodeButton = findViewById(R.id.get_code_button);
        comingRegisterButton = findViewById(R.id.coming_register_button);
        serviceNum = findViewById(R.id.service_num);

        findViewById(R.id.user_protocol).setOnClickListener(view ->
                WebActivity.start(RegisterActivity.this, API.USER_AGREEMENT, "用户服务协议"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

        initViews();

        deleteInput.setOnClickListener(v -> getUserNum.setText(""));

        //获取验证码
        getCodeButton.setOnClickListener(v -> {
            final String number = getUserNum.getText().toString();
            if (FuncUtils.isMobileNO(number)) {
                getPhoneCode(number);
            } else {
                showShortToast("手机号格式不正确");
            }
        });

        //呼叫客服
        serviceNum.setOnClickListener(v -> {
            AlertDialog alertDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setTitle("呼叫客服");
            builder.setMessage("您确认拨打客服电话？");
            builder.setPositiveButton("确认", (dialog, which) -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + serviceNum.getText().toString());
                intent.setData(data);
                startActivity(intent);
            });
            builder.setNegativeButton("暂不", null);
            alertDialog = builder.create();
            alertDialog.show();
        });

        //注册
        comingRegisterButton.setOnClickListener(v -> {
            if (getUserNum.getText().toString().equals("") || !FuncUtils.isMobileNO(getUserNum.getText().toString())) {
                showShortToast("手机号格式不正确");
            } else if (getCodeUser.getText().toString().equals("")) {
                showShortToast("请输入验证码");
            } else if (passwordInput.getText().toString().equals("")) {
                showShortToast("请输入密码");
            } else if (isOkPasswd.getText().toString().equals("")) {
                showShortToast("请输入确认密码");
            } else if (!passwordInput.getText().toString().equals(isOkPasswd.getText().toString())) {
                showShortToast("两次密码输入不一致");
            } else if (verificationCodeId.equals("") || !userNum.equals(getUserNum.getText().toString())) {
                showShortToast("请先获取验证码");
            } else {
                registerNewUser();
            }
        });
    }

    @Override
    public void getData() {

    }

    //获取短信验证码
    private void getPhoneCode(final String number) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
        }

        showLoading();
        NetService service = retrofit.create(NetService.class);
        service.getSMSVerification(getUserNum.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SMSVerificationResult>() {
                    @Override
                    public void onNext(SMSVerificationResult s) {
                        hideLoading();
                        TimeUtilKt.initCaptchaTimer(getCodeButton);
                        userNum = number;
                        verificationCodeId = s.getCode_id();
                        showShortToast("验证码已发送，请注意查收");
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void registerNewUser() {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
        }

        showLoading();
        NetService service = retrofit.create(NetService.class);
        service.commitRegistrInfo(getUserNum.getText().toString(), passwordInput.getText().toString().trim(), verificationCodeId, getCodeUser.getText().toString(),
                GlobalConstant.clientId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody o) {
                        hideLoading();
                        showShortToast("注册成功");
                        UserUtil.setMobile(getUserNum.getText().toString());
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Gson gson = new Gson();
                            try {
                                RegisterError error = gson.fromJson(body.string(), RegisterError.class);
                                showDialog(error);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
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
        builder.setPositiveButton("我知道了", (dialog, which) -> {

        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
