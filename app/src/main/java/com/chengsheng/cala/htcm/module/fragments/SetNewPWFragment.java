package com.chengsheng.cala.htcm.module.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.network.AccountService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.module.account.LoginActivity;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;


public class SetNewPWFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView title;
    private ImageView backLoginNpdButton;
    private TextView completeButton;
    private EditText newPassWord;
    private EditText okNewPassWord;

    private OnSetNewPWFFragmentInteractionListener mListener;

    private Retrofit retrofit;


    public SetNewPWFragment() {
        // Required empty public constructor
    }


    public static SetNewPWFragment newInstance(String param1, String param2) {
        SetNewPWFragment fragment = new SetNewPWFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_set_new_pw, container, false);

        title = rootView.findViewById(R.id.top_panel_sms).findViewById(R.id.menu_bar_title);
        backLoginNpdButton = rootView.findViewById(R.id.top_panel_sms).findViewById(R.id.back_login);
        completeButton = rootView.findViewById(R.id.complete_button);
        newPassWord = rootView.findViewById(R.id.new_pass_word);
        okNewPassWord = rootView.findViewById(R.id.ok_new_pass_word);

        title.setText("设置新密码");


        backLoginNpdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("EVENT", "click");
                bundle.putString("SOURCE", "back");
                onButtonPressed(bundle);
            }
        });
        //确认新密码
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPassWord.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "新密码不能为空", Toast.LENGTH_SHORT).show();
                } else if(okNewPassWord.getText().toString().equals("")){
                    Toast.makeText(getContext(), "确认密码不能为空", Toast.LENGTH_SHORT).show();
                }else if ((!newPassWord.getText().toString().equals("") &&
                        !okNewPassWord.getText().toString().equals("") )&&
                        !newPassWord.getText().toString().equals(okNewPassWord.getText().toString())) {
                    Toast.makeText(getContext(), "两次输入的密码不一致，请重新确认!", Toast.LENGTH_SHORT).show();
                } else {
                    updateNewWD();
                }
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onSetNewPWFFragmentInteraction(bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSetNewPWFFragmentInteractionListener) {
            mListener = (OnSetNewPWFFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSetNewPWFFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSetNewPWFFragmentInteraction(Bundle bundle);
    }

    private void updateNewWD() {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
        }

        AccountService accountService = retrofit.create(AccountService.class);

        accountService.setAccountPasswd(newPassWord.getText().toString(), "", "", "phone_number", mParam1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Toast.makeText(getContext(), "修改密码成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.putExtra("USER_NUMBER", mParam1);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                String str = body.string();
                                Toast.makeText(getContext(), "修改密码失败!" + str, Toast.LENGTH_SHORT).show();
                                Log.e("TAG", "修改密码失败:" + str);
                                Bundle bundle = new Bundle();
                                bundle.putString("EVENT", "click");
                                bundle.putString("SOURCE", "back");
                                onButtonPressed(bundle);
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