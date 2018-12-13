package com.chengsheng.cala.htcm.views.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.ExamReportItem;
import com.chengsheng.cala.htcm.model.datamodel.ExamReportList;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.views.activitys.ExamReportCompareActivity;
import com.chengsheng.cala.htcm.views.adapters.ExamReportRecyclerAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ExamReprotListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private LinearLayout childButtonContainer;
    private TextView reportCompare, cancelReportCompare, startReportCompare;
    private RecyclerView targetExamReportList;


    private Retrofit retrofit;

    private HTCMApp app;


    private boolean isCompare = false;

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

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        app = HTCMApp.create(getContext());
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
                        final List<ExamReportItem> items = examReportList.getItems();
                        final ExamReportRecyclerAdapter adapter = new ExamReportRecyclerAdapter(getContext(), items);
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
                                    Toast.makeText(getContext(), "没有可对比的报告数据", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        cancelReportCompare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int i = 0; i < items.size(); i++) {
                                    items.get(i).setSelect(false);
                                }
                                app.clearExams();
                                adapter.notifyDataSetChanged();
                                childButtonContainer.setVisibility(View.INVISIBLE);
                            }
                        });
                        Log.e("TAG", "请求完成:" + examReportList.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "请求错误:" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        childButtonContainer.setVisibility(View.INVISIBLE);


        startReportCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app.getExams() == null){
                    Log.e("TAG","获取要对比的报告为空!");
                    Toast.makeText(getContext(),"获取要对比的报告为空!",Toast.LENGTH_SHORT).show();
                }else if(app.getExams().isEmpty()){
                    Log.e("TAG","没有获取到要对比的报告的ID!");
                    Toast.makeText(getContext(),"没有获取到要对比的报告的ID!",Toast.LENGTH_SHORT).show();
                }else if(app.getExams().size() < 2){
                    Log.e("TAG","需要两个报告的ID");
                    Toast.makeText(getContext(),"需要两个报告的ID!",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("TAG","已选择的报告数:"+app.getExams().size());
                    Intent intent = new Intent(getContext(), ExamReportCompareActivity.class);
                    intent.putExtra("FIRST_ID",String.valueOf(app.getExams().get(0).getOrderId()));
                    intent.putExtra("FIRST_TIME",app.getExams().get(0).getIssued_date());
                    intent.putExtra("SECOND_ID",String.valueOf(app.getExams().get(1).getOrderId()));
                    intent.putExtra("SECOND_TIME",app.getExams().get(1).getIssued_date());
                    getContext().startActivity(intent);
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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
