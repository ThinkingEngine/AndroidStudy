package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.FamiliesList;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.AuthStateCallBack;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.views.adapters.FamiliesItemRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FamilyListFragment extends Fragment implements AuthStateCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;


    private HTCMApp app;
    private Retrofit getFamiliesList;
    private FamiliesItemRecyclerAdapter familiesItemRecyclerAdapter;

    private FamiliesList parentDatas;

    private String toeknType;
    private String AccessToken;

    private OnFamilyListInteractionListener mListener;

    public FamilyListFragment() {
    }

    public static FamilyListFragment newInstance(String param1, String param2) {
        FamilyListFragment fragment = new FamilyListFragment();
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

        CallBackDataAuth.setAuthStateCallBack(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootViews = inflater.inflate(R.layout.fragment_family_list, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = rootViews.findViewById(R.id.fresh_family_recycler);
        recyclerView = rootViews.findViewById(R.id.families_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        app = HTCMApp.create(getContext());
        //创建碎片时进行第一次网络访问。
        toeknType = app.getTokenType();
        AccessToken = app.getAccessToken();
        MyRetrofit myRetrofit = MyRetrofit.createInstance();
        if (getFamiliesList == null) {
            getFamiliesList = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = getFamiliesList.create(NetService.class);
        Log.e("FAMILIES","标签:"+toeknType+" "+ AccessToken);
        service.getFamiliesList(toeknType + " " + AccessToken)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<FamiliesList>() {
            @Override
            public void onNext(FamiliesList datas) {
                Log.e("FAMILIES", "家人列表信息 请求成功:" + datas.toString());
                parentDatas = datas;
                familiesItemRecyclerAdapter = new FamiliesItemRecyclerAdapter(getContext(),datas.getItems());
                recyclerView.setAdapter(familiesItemRecyclerAdapter);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("FAMILIES", "家人列表信息 请求失败:" + e);
            }

            @Override
            public void onComplete() {

            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "下拉刷新--", Toast.LENGTH_SHORT).show();
                familiesItemRecyclerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return rootViews;
    }

    public void onButtonPressed(Bundle data) {
        if (mListener != null) {
            mListener.onFamilyListInteraction(data);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFamilyListInteractionListener) {
            mListener = (OnFamilyListInteractionListener) context;
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
    public void authResult(Map<String, String> result) {
        Toast.makeText(getContext(),result.get("STATE")+":"+result.get("REASON"),Toast.LENGTH_LONG).show();
    }

    public interface OnFamilyListInteractionListener {
        // TODO: Update argument type and name
        void onFamilyListInteraction(Bundle data);
    }

}
