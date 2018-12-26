package com.chengsheng.cala.htcm.module.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.module.home.MainActivity;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.ZhiFuBaoSign;
import com.chengsheng.cala.htcm.utils.FuncUtils;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class ModePaymentActivity extends BaseActivity {

    private TextView title;
    private ImageView back;
    private TextView totalPayText;
    private RelativeLayout payModeZhifubaoBox;
    private ImageView selectPayModeButton;
    private TextView immediatePayButton;

    private String payMode = "";

    private HTCMApp app;
    private Retrofit retrofit;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String str = (String) msg.obj;
                Intent intent = new Intent(ModePaymentActivity.this, PayResultActivity.class);
                Bundle bundle = new Bundle();

                if (str.contains("resultStatus={9000}")) {
                    bundle.putInt(GlobalConstant.PAY_MARK, GlobalConstant.PAY_COMPLETE);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    bundle.putInt(GlobalConstant.PAY_MARK, GlobalConstant.PAY_FAILURE);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                Log.e("TAG", str);
            }

        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.activity_mode_payment;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        String orderID = getIntent().getStringExtra("ORDER_ID");
        String orderVal = getIntent().getStringExtra("ORDER_VAL");

        title = findViewById(R.id.title_header_mode_payment).findViewById(R.id.menu_bar_title);
        back = findViewById(R.id.title_header_mode_payment).findViewById(R.id.back_login);
        totalPayText = findViewById(R.id.total_pay_text);
        payModeZhifubaoBox = findViewById(R.id.pay_mode_zhifubao_box);
        selectPayModeButton = findViewById(R.id.select_pay_mode_button);
        immediatePayButton = findViewById(R.id.immediate_pay_button);

        title.setText("支付方式");
        totalPayText.setText("¥" + orderVal);

        payModeZhifubaoBox.setOnClickListener(v -> {
            if (selectPayModeButton.isSelected()) {
                selectPayModeButton.setSelected(false);
                payMode = "";
            } else {
                selectPayModeButton.setSelected(true);
                payMode = "ZFB";
            }
        });

        immediatePayButton.setOnClickListener(v -> {
            if (payMode.equals("")) {
                Toast.makeText(ModePaymentActivity.this, "请选择支付方式!", Toast.LENGTH_SHORT).show();
            } else {
                Thread payThread;
                payThread = new Thread(() -> {
                    PayTask payTask = new PayTask(ModePaymentActivity.this);

                    String result = payTask.pay(FuncUtils.getString("PAY_SIGN", ""), true);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                });
                payThread.start();
            }
        });

        getAliSign(orderID);
    }

    @Override
    public void getData() {

    }


    private void getAliSign(String orderId) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = retrofit.create(NetService.class);
        service.getAliSign(GlobalConstant.ALI_SIGN + orderId, app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ZhiFuBaoSign>() {
                    @Override
                    public void onNext(ZhiFuBaoSign zhiFuBaoSign) {
                        FuncUtils.putString("PAY_SIGN", zhiFuBaoSign.getAli_sign());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                Log.e("TAG", "获取支付宝签名失败，原因为:" + body.string());
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

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(ModePaymentActivity.this);
        builder.setTitle("提示");
        builder.setMessage("您的订单暂未完成支付无法生效，请尽快完成支付。");
        builder.setPositiveButton("立即支付", null);

        builder.setNegativeButton("确认返回", (dialog, which) -> startActivity(new MainActivity()));
        alertDialog = builder.create();
        alertDialog.show();
    }
}
