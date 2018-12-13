package com.chengsheng.cala.htcm.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.childmodela.ExamReportDetial;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.views.adapters.ExamAdviceExpandableListAdapter;
import com.chengsheng.cala.htcm.views.adapters.ExamResultRecyclerAdapter;
import com.chengsheng.cala.htcm.views.customviews.MyExpandableListView;
import com.chengsheng.cala.htcm.views.customviews.MyRecyclerView;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HTCMApp app = HTCMApp.create(getApplicationContext());
        setContentView(R.layout.activity_exam_report_detail);
        Bundle bundle = getIntent().getExtras();
        String titleName  = bundle.getString(GlobalConstant.EXAM_REPORT_NAME);
        String orderID = bundle.getString(GlobalConstant.EXAM_REPORT_ID);

        String url = "api/physical-exam-order/exam-result/"+orderID;
        if(retrofit == null){
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }
        NetService service = retrofit.create(NetService.class);
        service.getExamReportDetial(url,app.getTokenType()+" "+app.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ExamReportDetial>() {
                    @Override
                    public void onNext(ExamReportDetial examReportDetial) {
                        Log.e("TAG","请求成功:"+examReportDetial.toString());
                        setViews(examReportDetial);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","请求失败:"+e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        initViews();

        examReportDetailName.setText(titleName);


//        abnormalTips.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ExamReportDetailActivity.this,AbnormalUnscrambleActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void initViews(){
        backExamReport = findViewById(R.id.title_header_exam_report_detail).findViewById(R.id.back_login);
        examReportDetailName = findViewById(R.id.title_header_exam_report_detail).findViewById(R.id.menu_bar_title);
        examReportDetailPerson = findViewById(R.id.exam_report_detail_person);
        examReportDetailNum = findViewById(R.id.exam_report_detail_num);
        examReportDetailDate = findViewById(R.id.exam_report_detail_date);
        examAdvicesExpandable = findViewById(R.id.exam_advices_expandable);
        examResultList = findViewById(R.id.exam_result_list);

        //examAdvicesExpandable.getWidth() - 10
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
    }
}
