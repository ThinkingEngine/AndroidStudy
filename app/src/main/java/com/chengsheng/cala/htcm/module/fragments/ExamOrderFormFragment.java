package com.chengsheng.cala.htcm.module.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrder;
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrderItem;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.utils.CallBackDataAuth;
import com.chengsheng.cala.htcm.utils.UpdateConditionInterface;
import com.chengsheng.cala.htcm.utils.UpdateStateInterface;
import com.chengsheng.cala.htcm.adapter.ExamOrderFormRecyclerAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class ExamOrderFormFragment extends Fragment implements UpdateStateInterface, UpdateConditionInterface {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private XRecyclerView orderList;


    private Retrofit retrofit;
    private HTCMApp app;
    private ZLoadingDialog loadingDialog;

    private String mode = GlobalConstant.MODE_ALL;
    private int currentPage = 1;

    public ExamOrderFormFragment() {
        // Required empty public constructor
    }


    public static ExamOrderFormFragment newInstance(String param1, String param2) {
        ExamOrderFormFragment fragment = new ExamOrderFormFragment();
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
        CallBackDataAuth.setUpdateStateInterface(this);
        CallBackDataAuth.setUpdateConditionInterface(this);
        loadingDialog = new ZLoadingDialog(getContext());
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setLoadingColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintText("加载中....");
        loadingDialog.setDialogBackgroundColor(getContext().getResources().getColor(R.color.colorText));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exam_order_form, container, false);

        orderList = rootView.findViewById(R.id.order_list);
        networkQuest(currentPage, true);
        orderList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                networkQuest(1, false);
            }

            @Override
            public void onLoadMore() {
                networkQuest(currentPage++, false);
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
            networkQuest(1, true);
        }
    }

    @Override
    public void selectCondition(List<Map<String, String>> datas, boolean update) {
        if (update) {
            StringBuffer sb = new StringBuffer();
            for (Map<String, String> map : datas) {
                if (map != datas.get(datas.size() - 1)) {
                    sb.append(map.get("ID") + ",");
                } else {
                    sb.append(map.get("ID"));
                }
            }

            updateData(true, mode, sb.toString(), 1);
            Log.e("TAG", "字符测试:" + sb.toString());
        }

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void networkQuest(int page, boolean loading) {
        if (mParam1.equals("全部")) {
            mode = GlobalConstant.MODE_ALL;
        } else if (mParam1.equals("待付款")) {
            mode = GlobalConstant.MODE_RECEIVABLE;
        } else if (mParam1.equals("已付款")) {
            mode = GlobalConstant.MODE_RECEIVED;
        } else if (mParam1.equals("待评价")) {
            mode = GlobalConstant.MODE_COMMENT;
        } else if (mParam1.equals("已取消")) {
            mode = GlobalConstant.MODE_CANCEL;
        }
        updateData(loading, mode, "", page);
    }

    private void updateData(final boolean loading, String mode, String id, int page) {
        final int p = page;
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        if (loading) {
            loadingDialog.show();
        }
        NetService service = retrofit.create(NetService.class);

        service.getExamOrder(app.getTokenType() + " " + app.getAccessToken(), id, mode, String.valueOf(1))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamOrder>() {
                    @Override
                    public void onNext(ExamOrder examOrder) {
                        Log.e("TAG","getExamOrder成功");
                        List<ExamOrderItem> datas = examOrder.getItems();
                        ExamOrderFormRecyclerAdapter adapter = new ExamOrderFormRecyclerAdapter(getContext(), datas);
                        adapter.notifyDataSetChanged();
                        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
                        orderList.setAdapter(adapter);

                        if (loading) {
                            loadingDialog.cancel();
                        }

                        orderList.refreshComplete();
                        orderList.loadMoreComplete();
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("TAG","getExamOrder失败："+e.toString());
                        List<ExamOrderItem> datas = new ArrayList<>();
                        ExamOrderFormRecyclerAdapter adapter = new ExamOrderFormRecyclerAdapter(getContext(), datas);
                        orderList.setLayoutManager(new LinearLayoutManager(getContext()));
                        orderList.setAdapter(adapter);
                        if (loading) {
                            loadingDialog.cancel();
                        }
                        orderList.refreshComplete();
                        orderList.loadMoreComplete();
                    }

                    @Override
                    public void onComplete() {
                        if (loading) {
                            loadingDialog.cancel();
                        }
                        orderList.refreshComplete();
                        orderList.loadMoreComplete();
                    }
                });

    }
}
