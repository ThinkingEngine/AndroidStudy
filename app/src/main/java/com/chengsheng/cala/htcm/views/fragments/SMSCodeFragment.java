package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.SMSVerificationResult;
import com.chengsheng.cala.htcm.network.AccountService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.FuncUtils;
import com.chengsheng.cala.htcm.views.activitys.RegisterActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;


public class SMSCodeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnSMSCodeFragmentInteractionListener mListener;

    private String verificationCodeId = "";
    private String userPhone = "";
    private String code = "";

    private Retrofit retrofit;

    public SMSCodeFragment() {
        // Required empty public constructor
    }


    public static SMSCodeFragment newInstance(String param1, String param2) {
        SMSCodeFragment fragment = new SMSCodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_smscode, container, false);

        final TextView nextButton = rootView.findViewById(R.id.next_button);
        ImageView backLoginPdButton = rootView.findViewById(R.id.top_panel_sms).findViewById(R.id.back_login);
        final EditText getNumberSms = rootView.findViewById(R.id.get_number_sms);
        final EditText getCodeFormSms = rootView.findViewById(R.id.get_code_form_sms);
        final Button getCodeSmsButton = rootView.findViewById(R.id.get_code_sms_button);

        getCodeSmsButton.setBackground(getResources().getDrawable(R.drawable.code_button_bg));
        getCodeSmsButton.setTextColor(getResources().getColor(R.color.colorPrimary));

        //获取验证码!
        getCodeSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getNumberSms.getText().equals("")) {
                    Toast.makeText(getContext(), "手机号码不能为空!", Toast.LENGTH_SHORT).show();
                } else if (!FuncUtils.isMobileNO(getNumberSms.getText().toString())) {
                    Toast.makeText(getContext(), "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
                } else {
                    if (retrofit == null) {
                        retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
                        AccountService service = retrofit.create(AccountService.class);
                        service.getSMSVerification(getNumberSms.getText().toString(), "change_password")
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<SMSVerificationResult>() {
                            @Override
                            public void onNext(final SMSVerificationResult smsVerificationResult) {
                                Toast.makeText(getContext(), "请求发送成功，请等待短信消息", Toast.LENGTH_SHORT).show();
                                userPhone = getNumberSms.getText().toString();
                                verificationCodeId = smsVerificationResult.getCode_id();
                                code = getCodeFormSms.getText().toString();
                                nextButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (verificationCodeId.equals("")) {
                                            Toast.makeText(getContext(), "验证尚未成功，请稍等", Toast.LENGTH_SHORT).show();
                                        } else if (getCodeFormSms.getText().toString().equals("")) {
                                            Toast.makeText(getContext(), "请输入验证码!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
                                            AccountService accountService = retrofit.create(AccountService.class);
                                            Map<String, String> map = new HashMap<>();
                                            map.put("code", getCodeFormSms.getText().toString());
                                            map.put("code_id", verificationCodeId);
                                            map.put("type", "change_password");
                                            accountService.smsVerification(map)
                                                    .subscribeOn(Schedulers.newThread())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new DisposableObserver<ResponseBody>() {
                                                        @Override
                                                        public void onNext(ResponseBody responseBody) {
                                                            try {
                                                                Log.e("TAG", "onNext:短信验证结果" + responseBody.string());

                                                                Bundle bundle = new Bundle();
                                                                bundle.putString("EVENT", "click");
                                                                bundle.putString("SOURCE", "next");
                                                                bundle.putString("V_PHONE", getNumberSms.getText().toString());
                                                                bundle.putString("V_ID", smsVerificationResult.getCode_id());
                                                                bundle.putString("CODE", getCodeFormSms.getText().toString());
                                                                onButtonPressed(bundle);
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            if (e instanceof HttpException) {
                                                                ResponseBody body = ((HttpException) e).response().errorBody();
                                                                try {
                                                                    Toast.makeText(getContext(), "短信验证失败!请重新输入号码验证", Toast.LENGTH_SHORT).show();
                                                                    Log.e("TAG", "onError:短信验证结果" + body.string());
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
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (e instanceof HttpException) {
                                    ResponseBody body = ((HttpException) e).response().errorBody();
                                    try {
                                        String str = body.string();
                                        Toast.makeText(getContext(), "获取验证码失败!" + str, Toast.LENGTH_SHORT).show();
                                        Log.e("TAG", "获取验证码失败:" + str);
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
            }
        });

        getNumberSms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null) {
                    getCodeSmsButton.setBackground(getResources().getDrawable(R.drawable.code_button_gray_box));
                    getCodeSmsButton.setTextColor(getResources().getColor(R.color.colorThrText));
                }
                if (s.length() == 0) {
                    getCodeSmsButton.setBackground(getResources().getDrawable(R.drawable.code_button_bg));
                    getCodeSmsButton.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("EVENT", "click");
//                bundle.putString("SOURCE", "next");
//                onButtonPressed(bundle);
//            }
//        });

        backLoginPdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("EVENT", "click");
                bundle.putString("SOURCE", "backParent");
                onButtonPressed(bundle);
            }
        });

        return rootView;
    }


    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onSMSCodeFragmentInteraction(bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSMSCodeFragmentInteractionListener) {
            mListener = (OnSMSCodeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnSMSCodeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSMSCodeFragmentInteraction(Bundle bundle);
    }
}
