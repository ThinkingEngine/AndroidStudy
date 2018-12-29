package com.chengsheng.cala.htcm.module.activitys;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.data.repository.ExamReportRepository;
import com.chengsheng.cala.htcm.protocol.childmodela.ExamReportDetial;
import com.chengsheng.cala.htcm.adapter.ExamAdviceExpandableListAdapter;
import com.chengsheng.cala.htcm.adapter.ExamResultRecyclerAdapter;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
import com.chengsheng.cala.htcm.widget.MyExpandableListView;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;


import io.reactivex.observers.DefaultObserver;

/**
 * 个人体检报告 页面
 *
 * */

public class ExamReportDetailActivity extends BaseActivity {
    private TextView examReportDetailPerson;
    private TextView examReportDetailNum;
    private TextView examReportDetailDate;
    private Button downLoadDoc;
    private MyExpandableListView examAdvicesExpandable;
    private MyRecyclerView examResultList;

    private AppTitleBar atPersonReport;

    private HTCMApp app;


    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_report_detail;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        String orderID = bundle.getString(GlobalConstant.EXAM_REPORT_ID);

        initViews();
        getExamReportDetial(orderID);

    }

    @Override
    public void getData() {

    }

    private void initViews(){

        atPersonReport = findViewById(R.id.at_person_report);
        examReportDetailPerson = findViewById(R.id.exam_report_detail_person);
        examReportDetailNum = findViewById(R.id.exam_report_detail_num);
        examReportDetailDate = findViewById(R.id.exam_report_detail_date);
        examAdvicesExpandable = findViewById(R.id.exam_advices_expandable);
        examResultList = findViewById(R.id.exam_result_list);
        downLoadDoc = findViewById(R.id.down_load_doc);

        examAdvicesExpandable.setIndicatorBounds(examAdvicesExpandable.getWidth() - 140, 0);
        examAdvicesExpandable.setFocusable(false);
        examResultList.setNestedScrollingEnabled(false);

    }

    private void setViews(ExamReportDetial examReportDetial){
        examReportDetailPerson.setText(examReportDetial.getCustomer().getName());
        examReportDetailNum.setText("体检号："+examReportDetial.getCustomer().getRegistration().getId());
        examReportDetailDate.setText("体检日期："+examReportDetial.getCustomer().getRegistration().getDate());

        ExamAdviceExpandableListAdapter adapter = new ExamAdviceExpandableListAdapter(this,examReportDetial);
        examAdvicesExpandable.setAdapter(adapter);

        ExamResultRecyclerAdapter adapter1 = new ExamResultRecyclerAdapter(this,examReportDetial.getExam_item_result());
        examResultList.setLayoutManager(new LinearLayoutManager(this));
        examResultList.setAdapter(adapter1);
        examAdvicesExpandable.expandGroup(0);

        atPersonReport.setTitle(examReportDetial.getExam_report().getName());
        atPersonReport.setFinishClickListener(() -> finish());

        downLoadDoc.setOnClickListener(v -> downLoadDialog());
    }

    private void getExamReportDetial(String orderId){

        showLoading();
        ExamReportRepository
                .Companion.getDefault()
                .getExamResult(orderId)
                .subscribe(new DefaultObserver<ExamReportDetial>() {
            @Override
            public void onNext(ExamReportDetial examReportDetial) {
                setViews(examReportDetial);
                hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                Log.e("TAG","Throwable:"+e.toString());
                showError(e);
            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void downLoadDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("下载标准报告");
        builder.setMessage("你确认要下载标准报告?");
        builder.setNegativeButton("暂不",null);
        builder.setPositiveButton("下载", (dialog, which) -> {

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
