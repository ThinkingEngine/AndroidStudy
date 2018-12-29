package com.chengsheng.cala.htcm.module.account;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.activitys.ForgetPasswordActivity;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.LoginData;
import com.chengsheng.cala.htcm.utils.UserUtil;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {
    private TextView registerTV;
    private TextView loginTV;
    private TextView retrieveTV;
    private EditText cellphoneEdittext, passwordEdittext;
    private Button deleteInput, previewIcon;
    private TextView loginTelService;
    private ImageView outLoginPage;

    private boolean passVisible = false;

    private String userNameInput;
    private String passwordInput;

    /**
     * 打开登录页
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

        initViews();

        MyRetrofit myRetrofit = MyRetrofit.createInstance();
        Retrofit retrofit = myRetrofit.createDefault();
        final NetService service = myRetrofit.create(retrofit, NetService.class);

        RxTextView.textChanges(passwordEdittext).subscribe(new DefaultObserver<CharSequence>() {
            @Override
            public void onNext(CharSequence s) {
                deleteInput.setVisibility(s != null && !s.toString().isEmpty() ? View.VISIBLE :
                        View.INVISIBLE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //隐藏密码按钮
        previewIcon.setOnClickListener(v -> {
            if (!passVisible) {
                passVisible = true;
                passwordEdittext.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                passVisible = false;
                passwordEdittext.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        //清空密码框按钮
        deleteInput.setOnClickListener(v -> {
            passwordEdittext.setText("");
        });

        //注册功能按钮
        registerTV.setOnClickListener(v -> startActivity(new RegisterActivity()));

        //忘记密码按钮
        retrieveTV.setOnClickListener(v -> startActivity(new ForgetPasswordActivity()));

        outLoginPage.setOnClickListener(v -> finish());

        //登录
        loginTV.setOnClickListener(v -> {
            userNameInput = cellphoneEdittext.getText().toString();
            passwordInput = passwordEdittext.getText().toString();
            if (userNameInput.equals("") || passwordInput.equals("")) {
                showShortToast("请输入电话手机号");
                return;
            }
            showLoading();
            service.login(GlobalConstant.clientId,
                    GlobalConstant.grantType, userNameInput, passwordInput,
                    GlobalConstant.clientSecret, GlobalConstant.scope)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<LoginData>() {
                        @Override
                        public void onNext(LoginData data) {
                            hideLoading();
                            showShortToast("登录成功");
                            UserUtil.setAccessToken(data.getAccess_token());
                            UserUtil.setTokenType(data.getToken_type());
                            UserUtil.setMobile(userNameInput);
                            UserUtil.setPassword(passwordInput);
                            finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            hideLoading();
                            AlertDialog alertDialog;
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setTitle("登录失败");
                            builder.setMessage("登录失败：账户或密码错误");
                            builder.setPositiveButton("确认", null);
                            alertDialog = builder.create();
                            alertDialog.show();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        });

        loginTelService.setOnClickListener(v -> {
            AlertDialog alertDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("客服电话");
            builder.setMessage("您确定需要拨打客服电话!");
            builder.setPositiveButton("确定", (dialog1, which) -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + loginTelService.getText().toString());
                intent.setData(data);
                startActivity(intent);
            });

            builder.setNegativeButton("暂不", null);
            alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public void getData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserUtil.getMobile() != null && !UserUtil.getMobile().isEmpty()) {
            cellphoneEdittext.setText(UserUtil.getMobile());
        }
    }

    private void initViews() {
        registerTV = findViewById(R.id.register_button);
        loginTV = findViewById(R.id.login_button);
        retrieveTV = findViewById(R.id.retrieve_pw_button);
        cellphoneEdittext = findViewById(R.id.et_login_account);
        passwordEdittext = findViewById(R.id.et_login_password);
        deleteInput = findViewById(R.id.delete_input);
        previewIcon = findViewById(R.id.preview_icon);
        loginTelService = findViewById(R.id.login_tel_service);
        outLoginPage = findViewById(R.id.out_login_page);
        deleteInput.setVisibility(View.INVISIBLE);
    }
}
