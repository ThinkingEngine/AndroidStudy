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
import com.chengsheng.cala.htcm.views.adapters.FamiliesItemRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FamilyListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;


    private HTCMApp app;
    private Retrofit getFamiliesList;
    private FamiliesItemRecyclerAdapter familiesItemRecyclerAdapter;

    private FamiliesList parentDatas;

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
        app = HTCMApp.create(getContext());
        //创建碎片时进行第一次网络访问。
        MyRetrofit myRetrofit = MyRetrofit.createInstance();
        getFamiliesList = myRetrofit.createURL(GlobalConstant.API_BASE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootViews = inflater.inflate(R.layout.fragment_family_list, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = rootViews.findViewById(R.id.fresh_family_recycler);
        recyclerView = rootViews.findViewById(R.id.families_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        if (getFamiliesList != null) {
            NetService service = getFamiliesList.create(NetService.class);
            service.getFamiliesList(app.getTokenType() + " " + app.getAccessToken())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<FamiliesList>() {
                @Override
                public void onNext(FamiliesList datas) {
                    Log.e("FAMILIES", "请求成功:" + datas.toString());
                    parentDatas = datas;
                    familiesItemRecyclerAdapter = new FamiliesItemRecyclerAdapter(getContext(), datas.getItems());
                    recyclerView.setAdapter(familiesItemRecyclerAdapter);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("FAMILIES", "请求失败:" + e);
                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            Log.e("FAMILIES", "Retrofit实例化失败:");
        }


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

//    @Override
//    public void onStart() {
//        super.onStart();
////        recyclerView = rootViews.findViewById(R.id.families_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        if(parentDatas == null){
//            if (getFamiliesList != null) {
//                NetService service = getFamiliesList.create(NetService.class);
//                service.getFamiliesList(app.getTokenType() + " " + app.getAccessToken())
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<FamiliesList>() {
//                    @Override
//                    public void onNext(FamiliesList datas) {
//                        Log.e("FAMILIES", "请求成功:" + datas.toString());
//                        familiesItemRecyclerAdapter = new FamiliesItemRecyclerAdapter(getContext(), datas.getItems());
//                        recyclerView.setAdapter(familiesItemRecyclerAdapter);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("FAMILIES", "请求失败:" + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//            } else {
//                Log.e("FAMILIES", "Retrofit实例化失败:");
//            }
//        }else{
//            familiesItemRecyclerAdapter = new FamiliesItemRecyclerAdapter(getContext(), parentDatas.getItems());
//            recyclerView.setAdapter(familiesItemRecyclerAdapter);
//        }
//
//    }

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

    public interface OnFamilyListInteractionListener {
        // TODO: Update argument type and name
        void onFamilyListInteraction(Bundle data);
    }

}
