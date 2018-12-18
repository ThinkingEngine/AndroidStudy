package com.chengsheng.cala.htcm.module.activitys;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


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
    private MyExpandableListView examAdvicesExpandable;
    private MyRecyclerView examResultList;

    private Retrofit retrofit;
    private HTCMApp app;
    private ZLoadingDialog loadingDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_report_detail;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setHintText("加载中....");
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));


        Bundle bundle = getIntent().getExtras();
        String titleName  = bundle.getString(GlobalConstant.EXAM_REPORT_NAME);
        String orderID = bundle.getString(GlobalConstant.EXAM_REPORT_ID);

        initViews();
        getExamReportDetial(orderID);

        examReportDetailName.setText(titleName);
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
    }

    private void getExamReportDetial(String orderId){

        if(retrofit == null){
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        loadingDialog.show();
        NetService service = retrofit.create(NetService.class);
        service.getExamReportDetial(GlobalConstant.EXAM_RESULT+orderId,app.getTokenType()+" "+app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamReportDetial>() {
                    @Override
                    public void onNext(ExamReportDetial examReportDetial) {
                        setViews(examReportDetial);
                        loadingDialog.cancel();
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
}
