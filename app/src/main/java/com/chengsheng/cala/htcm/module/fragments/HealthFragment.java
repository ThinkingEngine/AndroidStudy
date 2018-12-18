package com.chengsheng.cala.htcm.module.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.FamiliesList;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.AuthStateCallBack;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.module.activitys.FamilyManageActivity;
import com.chengsheng.cala.htcm.adapter.FMRecyclerAdapter;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Author:
 * CreateDate:
 * Description: 健康模块
 */
public class HealthFragment extends Fragment implements UpdateStateInterface {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView peopleRecycler;

    private Retrofit getFamiliesList;
    private FMRecyclerAdapter fmRecyclerAdapter;
    private HTCMApp app;

    private OnFragmentInteractionListener mListener;

    private ZLoadingDialog loadingDialog;


    public HealthFragment() {
    }

    public static HealthFragment newInstance(String param1, String param2) {
        HealthFragment fragment = new HealthFragment();
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

        loadingDialog = new ZLoadingDialog(getContext());
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getContext().getResources().getColor(R.color.colorText));
        loadingDialog.setLoadingColor(getContext().getResources().getColor(R.color.colorPrimary));
        CallBackDataAuth.setUpdateStateInterface(this);
        app = HTCMApp.create(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_health, container, false);

        TextView title = rootView.findViewById(R.id.title_header_health).findViewById(R.id.menu_bar_title);
        final ImageView imageView = rootView.findViewById(R.id.title_header_health).findViewById(R.id.back_login);
        TextView childTitle = rootView.findViewById(R.id.title_header_health).findViewById(R.id.message_mark_text);
        peopleRecycler = rootView.findViewById(R.id.family_manage_recycler);
        final SwipeRefreshLayout freahPeopleRecycler = rootView.findViewById(R.id.fresh_people_recycler);

        title.setText("健康");
        imageView.setVisibility(View.INVISIBLE);
        childTitle.setText("家人管理");

        updateData();

        childTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FamilyManageActivity.class);
                intent.putExtra("ADD_MARK", false);
                startActivity(intent);
            }
        });


        freahPeopleRecycler.setColorSchemeColors(Color.BLUE);
        freahPeopleRecycler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
                freahPeopleRecycler.setRefreshing(false);
            }
        });

        return rootView;
    }

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

    @Override
    public void updateServiceMessage(boolean status) {
        if (status) {
            updateData();
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setViews(FamiliesList datas) {
        fmRecyclerAdapter = new FMRecyclerAdapter(getContext(), datas.getItems());
        peopleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        peopleRecycler.setAdapter(fmRecyclerAdapter);
    }

    private void updateData() {
        if (getFamiliesList == null) {
            getFamiliesList = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        loadingDialog.setHintText("加载中....");
        loadingDialog.show();
        NetService service = getFamiliesList.create(NetService.class);
        service.getFamiliesList(app.getTokenType() + " " + app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FamiliesList>() {
                    @Override
                    public void onNext(FamiliesList datas) {
                        Log.e("FAMILIES", datas.toString());
                        setViews(datas);
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                        Log.e("FAMILIES", e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
