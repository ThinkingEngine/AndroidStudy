package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.ExamItems;
import com.chengsheng.cala.htcm.model.datamodel.ExamItemsList;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.UpdateConditionInterface;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.views.adapters.MyExamRecyclerAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class MyExamAllFragment extends Fragment implements UpdateStateInterface,UpdateConditionInterface {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private Retrofit retrofit;

    private int currentPage = 1;
    private List<ExamItems> dataCollect;
    private MyExamRecyclerAdapter adapter;

    private XRecyclerView all;

    private String currentMode = "all";
    private String customerIds = "";

    private ZLoadingDialog loadingDialog;

    public MyExamAllFragment() {
    }


    public static MyExamAllFragment newInstance(String param1, String param2) {
        MyExamAllFragment fragment = new MyExamAllFragment();
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

        CallBackDataAuth.setUpdateStateInterface(this);
        CallBackDataAuth.setUpdateConditionInterface(this);
        loadingDialog = new ZLoadingDialog(getContext());
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("加载中....");
        loadingDialog.setHintTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setLoadingColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getContext().getResources().getColor(R.color.colorText));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootViews = inflater.inflate(R.layout.fragment_my_exam_all, container, false);
        all = rootViews.findViewById(R.id.my_exam_all_state);

        if (mParam1.equals("全部")) {
            updateData(true);
        } else if (mParam1.equals("待体检")) {
            currentMode = "reservation";
            getDataByMode("reservation");
        } else if (mParam1.equals("体检中")) {
            currentMode = "checking";
            getDataByMode("checking");
        } else {
            currentMode = "cancel";
            getDataByMode("cancel");
        }

        all.setLoadingMoreEnabled(true);
        all.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                updateData(false);
            }

            @Override
            public void onLoadMore() {
                NetService service = retrofit.create(NetService.class);
                currentPage++;
                if (currentMode.equals("all")) {
                    service.getExamListMore(mParam2, String.valueOf(currentPage))
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<ExamItemsList>() {
                                @Override
                                public void onNext(ExamItemsList examItemsList) {
                                    dataCollect.addAll(examItemsList.getItems());
                                    adapter.notifyDataSetChanged();
                                    all.loadMoreComplete();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    all.loadMoreComplete();
                                }

                                @Override
                                public void onComplete() {
                                    all.loadMoreComplete();
                                }
                            });
                } else {
                    getDataByModeMore(currentMode);
                }
            }
        });


        return rootViews;
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
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
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
            updateData(true);
        }
    }

    @Override
    public void selectCondition(List<Map<String, String>> datas, boolean update) {
        if(update){
            StringBuffer sb = new StringBuffer();
            for(Map<String,String> data:datas){
                if(datas.get(datas.size()-1) == data){
                    sb.append(data.get("ID"));
                }else{
                    sb.append(data.get("ID")+",");
                }
                Log.e("TAG","获取数据为:"+data.get("DATA"));
            }
            customerIds = sb.toString();
            updateData(true);
            Log.e("TAG","获取数据测试:"+sb.toString());
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void updateData(final boolean loading) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        if (loading) {
            loadingDialog.show();
        }
        all.refreshComplete();
        NetService service = retrofit.create(NetService.class);
        Log.e("TAG","customerIds数据测试:"+customerIds);
        service.getExamListFilter(mParam2,customerIds)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamItemsList>() {
                    @Override
                    public void onNext(ExamItemsList examItemsList) {
                        if (loading) {
                            loadingDialog.cancel();
                        }
                        all.refreshComplete();
                        Log.e("TAG","查询测试:"+examItemsList.toString());
                        dataCollect = examItemsList.getItems();
                        adapter = new MyExamRecyclerAdapter(getContext(), examItemsList.getItems());
                        adapter.notifyDataSetChanged();
                        all.setLayoutManager(new LinearLayoutManager(getContext()));
                        all.setAdapter(adapter);

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (loading) {
                            loadingDialog.cancel();
                        }
                        all.refreshComplete();
                    }

                    @Override
                    public void onComplete() {
                        if (loading) {
                            loadingDialog.cancel();
                        }
                        all.refreshComplete();
                    }
                });
    }

    private void getDataByMode(String mode) {
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        loadingDialog.show();
        NetService service = retrofit.create(NetService.class);
        service.getExamListMode(mParam2, mode,customerIds)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamItemsList>() {
                    @Override
                    public void onNext(ExamItemsList examItemsList) {
                        loadingDialog.cancel();
                        dataCollect = examItemsList.getItems();
                        adapter = new MyExamRecyclerAdapter(getContext(), examItemsList.getItems());
                        adapter.notifyDataSetChanged();
                        all.setLayoutManager(new LinearLayoutManager(getContext()));
                        all.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.cancel();
                    }

                    @Override
                    public void onComplete() {
                        loadingDialog.cancel();
                    }
                });
    }

    private void getDataByModeMore(String mode) {
        NetService service = retrofit.create(NetService.class);
        currentPage++;
        if (currentMode.equals("all")) {
            service.getExamListModeMode(mParam2, mode, String.valueOf(currentPage),customerIds)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<ExamItemsList>() {
                        @Override
                        public void onNext(ExamItemsList examItemsList) {
                            dataCollect.addAll(examItemsList.getItems());
                            adapter.notifyDataSetChanged();
                            all.loadMoreComplete();

                        }

                        @Override
                        public void onError(Throwable e) {
                            all.loadMoreComplete();
                        }

                        @Override
                        public void onComplete() {
                            all.loadMoreComplete();
                        }
                    });
        }

    }
}
