package com.chengsheng.cala.htcm.module.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.ExamReportItem;
import com.chengsheng.cala.htcm.protocol.ExamReportList;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.module.activitys.ExamReportCompareActivity;
import com.chengsheng.cala.htcm.adapter.ExamReportRecyclerAdapter;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.ToastUtil;
import com.suke.widget.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-21 14:14
 * Description:体检报告列表
 */

public class ExamReprotListFragment extends Fragment implements ExamReportRecyclerAdapter.ReportSeclectListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private LinearLayout childButtonContainer;
    private TextView reportCompare, cancelReportCompare, startReportCompare;
    private RecyclerView targetExamReportList;

    private List<ExamReportItem> items, selectReports;
    private ExamReportRecyclerAdapter adapter;

    private Retrofit retrofit;


    public ExamReprotListFragment() {

    }

    public static ExamReprotListFragment newInstance(String param1, String param2) {
        ExamReprotListFragment fragment = new ExamReprotListFragment();
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

        selectReports = new ArrayList<>();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootViews = inflater.inflate(R.layout.fragment_exam_reprot_list, container, false);
        childButtonContainer = rootViews.findViewById(R.id.child_button_container);
        reportCompare = rootViews.findViewById(R.id.report_compare);
        cancelReportCompare = rootViews.findViewById(R.id.cancel_report_compare);
        startReportCompare = rootViews.findViewById(R.id.start_report_compare);
        targetExamReportList = rootViews.findViewById(R.id.target_exam_report_list);


        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        targetExamReportList.setLayoutManager(new LinearLayoutManager(getContext()));


        NetService service = retrofit.create(NetService.class);
        service.getIssuedReport(mParam2, mParam1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamReportList>() {
                    @Override
                    public void onNext(final ExamReportList examReportList) {
                        items = examReportList.getItems();
                        adapter = new ExamReportRecyclerAdapter(getContext(), items, ExamReprotListFragment.this);
                        targetExamReportList.setAdapter(adapter);
                        reportCompare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!items.isEmpty()) {
                                    for (int i = 0; i < items.size(); i++) {
                                        items.get(i).setSelect(true);
                                    }
                                    adapter.notifyDataSetChanged();
                                    childButtonContainer.setVisibility(View.VISIBLE);
                                } else {
                                    ToastUtil.showShortToast(getContext(),"没有可对比的报告数据!");
                                }
                            }
                        });

                        cancelReportCompare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < items.size(); i++) {
                                    items.get(i).setSelect(false);
                                }
                                adapter.notifyDataSetChanged();
                                adapter.count = 0;
                                childButtonContainer.setVisibility(View.INVISIBLE);
                            }
                        });
                        Log.e("TAG", "请求完成:" + examReportList.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showShortToast(getContext(),"网络请求异常！请检查网络");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        childButtonContainer.setVisibility(View.INVISIBLE);


        startReportCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectReports.isEmpty()) {
                    if(BuildConfig.DEBUG){
                        Log.e("TAG", "获取要对比的报告为空!");
                    }
                    ToastUtil.showShortToast(getContext(),"获取要对比的报告为空!");
                } else if (selectReports.size() < 2) {
                    if(BuildConfig.DEBUG){
                        Log.e("TAG", "需要两个报告的ID:" + selectReports.size());
                    }
                    ToastUtil.showShortToast(getContext(),"需要两个报告的ID!");
                } else {
                    if(BuildConfig.DEBUG){
                        Log.e("TAG", "已选择的报告数:" + selectReports.size());
                    }

                    Bundle bundle = new Bundle();
                    bundle.putString("FIRST_ID", String.valueOf(selectReports.get(0).getOrderId()));
                    bundle.putString("FIRST_TIME", selectReports.get(0).getIssued_date());
                    bundle.putString("SECOND_ID", String.valueOf(selectReports.get(1).getOrderId()));
                    bundle.putString("SECOND_TIME", selectReports.get(1).getIssued_date());
                    ActivityUtil.Companion.startActivity(getContext(),new ExamReportCompareActivity(),bundle);
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
    public void reportSelect(ExamReportItem reports,boolean add) {
        if(add){
            selectReports.add(reports);
        }else{
            selectReports.remove(reports);
        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
