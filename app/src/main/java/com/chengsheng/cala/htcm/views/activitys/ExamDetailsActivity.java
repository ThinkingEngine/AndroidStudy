package com.chengsheng.cala.htcm.views.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.BaseActivity;
import com.chengsheng.cala.htcm.GlobalConstant;
import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.model.datamodel.childmodelb.UserExamDetail;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.views.adapters.ExamItemExpandableListViewAdapter;
import com.chengsheng.cala.htcm.views.customviews.MyExpandableListView;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class ExamDetailsActivity extends BaseActivity {
    private ImageView backButton;
    private TextView menuBarTitle;
    private MyExpandableListView examItemExpandable;
    private TextView examStatsMark;
    private TextView examPerson, examNumDetail, examCompany, examCompanyAddress;
    private TextView examComboName, examDateDetail;
    private ImageView examCodeDetail;
    private TextView checkExamRepot;
    private LinearLayout reportWaitNumBox;
    private TextView userNeedNote;
    private TextView examItemNum;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HTCMApp app = HTCMApp.create(getApplicationContext());
        setContentView(R.layout.activity_exam_details);
        initViews();

        String orderID = getIntent().getStringExtra("ORDER_ID");
        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        NetService service = retrofit.create(NetService.class);
        service.getUserExamDetail(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.USER_EXAM_DETAIL + orderID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserExamDetail>() {
                    @Override
                    public void onNext(UserExamDetail userExamDetail) {
                        Log.e("TAG", "体检详情数据请求成功:" + userExamDetail.toString());
                        setViews(userExamDetail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                Log.e("TAG", "体检详情数据请求失败:" + body.string());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @SuppressLint("CutPasteId")
    private void initViews() {
        backButton = findViewById(R.id.title_header_exam_details).findViewById(R.id.back_login);
        menuBarTitle = findViewById(R.id.title_header_exam_details).findViewById(R.id.menu_bar_title);
        examItemExpandable = findViewById(R.id.exam_item_expandable);
        examStatsMark = findViewById(R.id.exam_stats_mark);
        examPerson = findViewById(R.id.exam_person);
        examNumDetail = findViewById(R.id.exam_num_detail);
        examCompany = findViewById(R.id.exam_company);
        examCompanyAddress = findViewById(R.id.exam_company_address);
        examComboName = findViewById(R.id.exam_combo_name);
        examDateDetail = findViewById(R.id.exam_date_detail);
        examCodeDetail = findViewById(R.id.exam_code_detail);
        checkExamRepot = findViewById(R.id.check_exam_repot);
        reportWaitNumBox = findViewById(R.id.report_wait_num_box);
        userNeedNote = findViewById(R.id.user_need_note);
        examItemNum = findViewById(R.id.exam_item_num);

        menuBarTitle.setText("详情");
        reportWaitNumBox.setVisibility(View.INVISIBLE);
        examCodeDetail.setVisibility(View.VISIBLE);
    }

    private void setViews(final UserExamDetail userExamDetail) {
        final String examStats = userExamDetail.getExam_status();
        if (examStats.equals(GlobalConstant.RESERVATION)) {
            examStatsMark.setText("待体检");
            examStatsMark.setTextColor(getResources().getColor(R.color.colorBule));
            checkExamRepot.setText("检前须知");
        } else if (examStats.equals(GlobalConstant.CHECKING)) {
            examStatsMark.setText("体检中");
            examStatsMark.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            checkExamRepot.setText("进入导检");
        } else {
            examStatsMark.setText("已体检");
            checkExamRepot.setText("查看报告");
            if (userExamDetail.getReport().isIssued()) {
                checkExamRepot.setBackground(getResources().getDrawable(R.drawable.login_button_bg));
            } else {
                checkExamRepot.setBackground(getResources().getDrawable(R.drawable.gray_gradient_button_bg));
                reportWaitNumBox.setVisibility(View.VISIBLE);
                examCodeDetail.setVisibility(View.INVISIBLE);
            }

        }

        checkExamRepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(examStats.equals(GlobalConstant.RESERVATION)){
                    Intent intent = new Intent(ExamDetailsActivity.this,BeforeExamActivity.class);
                    intent.putExtra("EXAM_ID",String.valueOf(userExamDetail.getId()));
                    startActivity(intent);
                }else if(examStats.equals(GlobalConstant.CHECKING)){
                    Intent intent = new Intent(ExamDetailsActivity.this,IntelligentCheckActivity.class);
                    intent.putExtra("EXAM_ID",String.valueOf(userExamDetail.getId()));
                    startActivity(intent);
                }
            }
        });

        if (!userExamDetail.getNotices().isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for (String content : userExamDetail.getNotices()) {
                sb.append(content + "\n");
            }
            userNeedNote.setText(sb.toString());
        } else {
            userNeedNote.setText("暂无内容！");
        }

        ExamItemExpandableListViewAdapter adapter = new ExamItemExpandableListViewAdapter(this,userExamDetail.getExam_item());
        examItemExpandable.setAdapter(adapter);
        examItemExpandable.setIndicatorBounds(examItemExpandable.getWidth() - 140, examItemExpandable.getWidth() - 10);

        examPerson.setText(userExamDetail.getCustomer().getName());
        examNumDetail.setText(userExamDetail.getCustomer().getReservation_or_registration().getId());
        examCompany.setText(userExamDetail.getOrganization().getName());
        examCompanyAddress.setText(userExamDetail.getOrganization().getAddress());
        examComboName.setText(userExamDetail.getName());
        examDateDetail.setText("体检日期："+userExamDetail.getCustomer().getReservation_or_registration().getDate());
        examItemNum.setText("("+userExamDetail.getExam_item().size()+"项)");

    }
}
