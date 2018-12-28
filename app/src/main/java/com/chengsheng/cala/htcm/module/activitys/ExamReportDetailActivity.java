package com.chengsheng.cala.htcm.module.activitys;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.protocol.childmodela.ExamReportDetial;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.adapter.ExamAdviceExpandableListAdapter;
import com.chengsheng.cala.htcm.adapter.ExamResultRecyclerAdapter;
import com.chengsheng.cala.htcm.widget.MyExpandableListView;
import com.chengsheng.cala.htcm.widget.MyRecyclerView;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ExamReportDetailActivity extends BaseActivity {
    private ImageView backExamReport;
    private TextView examReportDetailName;
    private TextView examReportDetailPerson;
    private TextView examReportDetailNum;
    private TextView examReportDetailDate;
    private Button downLoadDoc;
    private MyExpandableListView examAdvicesExpandable;
    private MyRecyclerView examResultList;

    private Retrofit retrofit;
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
        backExamReport = findViewById(R.id.title_header_exam_report_detail).findViewById(R.id.back_login);
        examReportDetailName = findViewById(R.id.title_header_exam_report_detail).findViewById(R.id.menu_bar_title);
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
        examReportDetailName.setText(examReportDetial.getExam_report().getName());

        downLoadDoc.setOnClickListener(v -> downLoadDialog());
    }

    private void getExamReportDetial(String orderId){

        if(retrofit == null){
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        showLoading();
        NetService service = retrofit.create(NetService.class);
        service.getExamReportDetial(GlobalConstant.EXAM_RESULT+orderId,app.getTokenType()+" "+app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamReportDetial>() {
                    @Override
                    public void onNext(ExamReportDetial examReportDetial) {
                        setViews(examReportDetial);
                        hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
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
