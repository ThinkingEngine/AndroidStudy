package com.chengsheng.cala.htcm.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.TextMessage;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class ImmediatelyDialogView extends Dialog {
    private Context context;
    private int id;


    public ImmediatelyDialogView(Context context, int ID) {
        super(context);
        this.context = context;
        this.id = ID;
    }

    public ImmediatelyDialogView(Context context, int ID, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.id = ID;
    }

    protected ImmediatelyDialogView(Context context, int ID, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.id = ID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.dialog_immediately_certification_layout, null);
        setContentView(rootView);

        TextView certificationText = rootView.findViewById(R.id.certification_text);
        Button cancelCer = rootView.findViewById(R.id.cancel_certification_button);
        Button certification = rootView.findViewById(R.id.certification_button);
        final EditText inputBoxCertification = rootView.findViewById(R.id.input_box_certification);


        String str_start = "\"认证码\"是本软件中将体检人和体检数据相互关联的唯一标识码，需要体检人到体检机构进行认证以后获得。" +
                "为了保护体检人的健康隐私，只有在体检人成功认证后，用户才可以查看其体检数据。若您还未取得“认证码”，" +
                "请前往体检登记处或拨打客服电话";
        String str_tagert = "028-7654321";
        TelClickableSpan telClickableSpan = new TelClickableSpan(str_tagert, getContext());
        SpannableString spannTagert = new SpannableString(str_tagert);
        spannTagert.setSpan(telClickableSpan, 0, str_tagert.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        certificationText.setText(str_start);
        certificationText.append(spannTagert);
        certificationText.append("进行咨询。");
        certificationText.setMovementMethod(LinkMovementMethod.getInstance());

        cancelCer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        certification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputBoxCertification.getText().toString().equals("")) {
                    inputBoxCertification.setText("");
                    Toast.makeText(context, "请输入验证码！", Toast.LENGTH_SHORT).show();
                } else {
                    String code = inputBoxCertification.getText().toString();
                    HTCMApp app = HTCMApp.create(context);
                    MyRetrofit myRetrofit = MyRetrofit.createInstance();
                    Retrofit retrofit = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
                    NetService service = retrofit.create(NetService.class);
                    RequestBody codeBody = RequestBody.create(MediaType.parse("multipart/form-data"), code);
                    Map<String, RequestBody> mapa = new HashMap<>();
                    mapa.put("auth_code", codeBody);
                    service.authenticationFamilies(app.getTokenType() + " " + app.getAccessToken(), String.valueOf(id), mapa)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<ResponseBody>() {
                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    Log.e("AUTH", "验证成功" + responseBody.toString());
                                    Toast.makeText(context, "验证成功:" + responseBody.toString(), Toast.LENGTH_SHORT).show();
                                    CallBackDataAuth.doAuthStateCallBack(true);
                                    dismiss();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (e instanceof HttpException) {
                                        ResponseBody body = ((HttpException) e).response().errorBody();
                                        try {
                                            String info = body.string();
                                            Gson gson = new Gson();
                                            TextMessage message = gson.fromJson(info, TextMessage.class);
                                            Toast.makeText(context, "验证失败:" + message.toString(), Toast.LENGTH_SHORT).show();
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

            }
        });

        setCancelable(true);
    }

    class TelClickableSpan extends ClickableSpan {
        private String str;
        private Context context;

        public TelClickableSpan(String str, Context context) {
            super();
            this.context = context;
            this.str = str;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(context.getResources().getColor(R.color.colorPrimary));
        }

        @Override
        public void onClick(View widget) {

            Toast.makeText(context, "点击测试", Toast.LENGTH_SHORT).show();

        }
    }
}
