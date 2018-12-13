package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.businesslogic.DataFlow;
import com.chengsheng.cala.htcm.model.datamodel.childmodela.UserInfo;
import com.chengsheng.cala.htcm.network.AccountService;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.views.activitys.AccountSettingActivity;
import com.chengsheng.cala.htcm.views.activitys.ExamOrderFormActivity;
import com.chengsheng.cala.htcm.views.activitys.ServiceMessageActivity;
import com.chengsheng.cala.htcm.views.activitys.ServiceOrderActivity;
import com.chengsheng.cala.htcm.views.adapters.MineItemBaseAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MineFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView medicalExamOrderText;
    private TextView userNameText;
    private TextView userCellphoneNum;
    private ImageView inputPersonalInfo;
    private SimpleDraweeView userIcon;
    private FrameLayout messageIconContainerMine;
    private ImageView currentHasMessageMine;

    private Retrofit retrofit;
    private HTCMApp app;

    private int[] iconsA = new int[]{R.mipmap.jiarengaunli, R.mipmap.shoucang, R.mipmap.tuikuanjilu, R.mipmap.wodeshebei};
    private int[] iconsB = new int[]{R.mipmap.changjianwenti, R.mipmap.shezhi, R.mipmap.lianxikefu};
    private String[] titleA = new String[]{"家人管理", "收藏", "退款记录", "我的设备"};
    private String[] titleB = new String[]{"常见问题", "设置", "联系客服"};
    private String[] typeA = new String[]{"GENERAL", "GENERAL", "GENERAL", "GENERAL"};
    private String[] typeB = new String[]{"GENERAL", "GENERAL", "TEL"};

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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

        app = HTCMApp.create(getContext());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        ListView groupA = rootView.findViewById(R.id.mine_group_a);
        ListView groupB = rootView.findViewById(R.id.mine_group_b);
        medicalExamOrderText = rootView.findViewById(R.id.medical_exam_order_text);//体检订单文本
        TextView serviceOrderText = rootView.findViewById(R.id.service_order_text);
        userNameText = rootView.findViewById(R.id.user_name_text);
        userCellphoneNum = rootView.findViewById(R.id.user_cellphone_num);
        inputPersonalInfo = rootView.findViewById(R.id.input_personal_info);
        userIcon = rootView.findViewById(R.id.user_icon);
        messageIconContainerMine = rootView.findViewById(R.id.message_icon_container_mine);
        currentHasMessageMine = rootView.findViewById(R.id.current_has_message_mine);


        updateInfo();

        messageIconContainerMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ServiceMessageActivity.class);
                getContext().startActivity(intent);
            }
        });

        List<Map<String, String>> dataGroupA = DataFlow.minePageItemModelA(4, iconsA, titleA, typeA);
        List<Map<String, String>> dataGroupB = DataFlow.minePageItemModelA(3, iconsB, titleB, typeB);

        groupA.setAdapter(new MineItemBaseAdapter(getContext(), dataGroupA));
        groupB.setAdapter(new MineItemBaseAdapter(getContext(), dataGroupB));


        serviceOrderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ServiceOrderActivity.class);
                getContext().startActivity(intent);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void updateInfo(){
        if(retrofit == null){
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.TEST_URL);
        }

        AccountService service = retrofit.create(AccountService.class);
        service.getUserInfo(app.getTokenType()+" "+app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserInfo>() {
                    @Override
                    public void onNext(final UserInfo userInfo) {
                        setViews(userInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","用户信息请求失败"+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void setViews(final UserInfo userInfo){
        userIcon.setImageURI(userInfo.getAvatar_url());
        userNameText.setText(userInfo.getNickname());
        userCellphoneNum.setText(userInfo.getPhone_number());

        Log.e("TAG","CUSTOMER_ID:"+userInfo.toString());

        medicalExamOrderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ExamOrderFormActivity.class);
                intent.putExtra("CUSTOMER_ID",String.valueOf(userInfo.getId()));
                getContext().startActivity(intent);
            }
        });

        inputPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AccountSettingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("USER_INFO",userInfo);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
    }

}
