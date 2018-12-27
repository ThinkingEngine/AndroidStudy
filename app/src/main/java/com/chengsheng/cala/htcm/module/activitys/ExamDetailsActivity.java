package com.chengsheng.cala.htcm.module.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.HTCMApp;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.ExamItemExpandableListViewAdapter;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.network.MyRetrofit;
import com.chengsheng.cala.htcm.network.NetService;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.protocol.childmodelb.UserExamDetail;
import com.chengsheng.cala.htcm.widget.MyExpandableListView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * 体检详情
 */
public class ExamDetailsActivity extends BaseActivity {
    private MyExpandableListView examItemExpandable;
    private TextView examStatsMark;
    private TextView examPerson, examNumDetail, examCompany, examCompanyAddress;
    private TextView examComboName, examDateDetail;
    private ImageView examCodeDetail;
    private TextView checkExamRepot;
    private LinearLayout reportWaitNumBox;
    private TextView userNeedNote;
    private TextView examItemNum;
    private TextView examStateText;
    private SwipeRefreshLayout refreshExamDetail;
    private LinearLayout rButtonBox;

    private Retrofit retrofit;
    private HTCMApp app;

    @Override
    public int getLayoutId() {
        return R.layout.activity_exam_details;
    }

    @Override
    public void initView() {
        app = HTCMApp.create(getApplicationContext());

        String orderID = getIntent().getStringExtra("ORDER_ID");

        initViews(orderID);

        getUserExamDetail(orderID);
    }

    @Override
    public void getData() {

    }

    @SuppressLint("CutPasteId")
    private void initViews(final String orderId) {
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
        examStateText = findViewById(R.id.exam_state_text);
        refreshExamDetail = findViewById(R.id.refresh_exam_detail);
        rButtonBox = findViewById(R.id.r_button_box);

        reportWaitNumBox.setVisibility(View.INVISIBLE);
        examCodeDetail.setVisibility(View.VISIBLE);

        examItemExpandable.setFocusable(false);

        refreshExamDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserExamDetail(orderId);
                refreshExamDetail.setRefreshing(false);
            }
        });
    }

    private void setViews(final UserExamDetail userExamDetail) {
        final String examStats = userExamDetail.getExam_status();
        if (examStats.equals(GlobalConstant.RESERVATION)) {
            examStatsMark.setText("待体检");
            examStatsMark.setTextColor(getResources().getColor(R.color.colorBule));
            checkExamRepot.setText("检前须知");
            examCodeDetail.setImageResource(R.mipmap.erweima);
            examStateText.setText("预约号：");
        } else if (examStats.equals(GlobalConstant.CHECKING)) {
            examStatsMark.setText("体检中");
            examStateText.setText("体检号：");
            examStatsMark.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            checkExamRepot.setText("进入导检");
            examCodeDetail.setImageResource(R.mipmap.tianxingma);
        } else {
            examStatsMark.setText("已体检");
            checkExamRepot.setText("查看报告");
            examStateText.setText("体检号：");
            examCodeDetail.setImageResource(R.mipmap.tianxingma);
            if (userExamDetail.getReport().isIssued()) {
                checkExamRepot.setBackground(getResources().getDrawable(R.drawable.login_button_bg));
            } else {
                checkExamRepot.setBackground(getResources().getDrawable(R.drawable.gray_gradient_button_bg));
                reportWaitNumBox.setVisibility(View.VISIBLE);
                examCodeDetail.setVisibility(View.INVISIBLE);
            }

        }

        if (userExamDetail.isCan_autonomous()) {
            rButtonBox.setVisibility(View.VISIBLE);
        } else {
            rButtonBox.setVisibility(View.INVISIBLE);
        }

        //检前须知
        checkExamRepot.setOnClickListener(v -> {
            switch (examStats) {
                case GlobalConstant.RESERVATION: {
                    Intent intent = new Intent(ExamDetailsActivity.this, BeforeExamActivity.class);
                    intent.putExtra("EXAM_ID", String.valueOf(userExamDetail.getId()));
                    startActivity(intent);
                    break;
                }
                case GlobalConstant.CHECKING: {
                    Intent intent = new Intent(ExamDetailsActivity.this, IntelligentCheckActivity.class);
                    intent.putExtra("EXAM_ID", String.valueOf(userExamDetail.getId()));
                    startActivity(intent);
                    break;
                }
                case GlobalConstant.CHECKED: {
                    Intent intent = new Intent(ExamDetailsActivity.this, ExamReportDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(GlobalConstant.EXAM_REPORT_ID, String.valueOf(userExamDetail.getId()));
                    bundle.putString(GlobalConstant.EXAM_REPORT_NAME, userExamDetail.getName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
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

        examCodeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (examStats.equals(GlobalConstant.RESERVATION)) {
                    FamiliesListItem familiesListItem = new FamiliesListItem();
                    familiesListItem.setHealth_card_no(userExamDetail.getCustomer().getReservation_or_registration().getId());
                    Intent intent = new Intent(ExamDetailsActivity.this, UserCardActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("FAMILIES_INFO", familiesListItem);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ExamDetailsActivity.this, BarCodeActivity.class);
                    intent.putExtra("FAMILIES_INFO", userExamDetail.getCustomer().getReservation_or_registration().getId());
                    startActivity(intent);
                }
            }
        });

        ExamItemExpandableListViewAdapter adapter = new ExamItemExpandableListViewAdapter(this, userExamDetail.getExam_item(), GlobalConstant.EXAM_DETAIL_MARK);
        examItemExpandable.setAdapter(adapter);
        examItemExpandable.setIndicatorBounds(examItemExpandable.getWidth() - 140, examItemExpandable.getWidth() - 10);

        examPerson.setText(userExamDetail.getCustomer().getName());
        examNumDetail.setText(userExamDetail.getCustomer().getReservation_or_registration().getId());
        examCompany.setText(userExamDetail.getOrganization().getName());
        examCompanyAddress.setText(userExamDetail.getOrganization().getAddress());
        examComboName.setText(userExamDetail.getName());
        examDateDetail.setText("体检日期：" + userExamDetail.getCustomer().getReservation_or_registration().getDate());
        examItemNum.setText("(" + userExamDetail.getExam_item().size() + "项)");

    }

    private void getUserExamDetail(String orderId) {

        if (retrofit == null) {
            retrofit = MyRetrofit.createInstance().createURL(GlobalConstant.API_BASE_URL);
        }

        showLoading();
        NetService service = retrofit.create(NetService.class);
        service.getUserExamDetail(app.getTokenType() + " " + app.getAccessToken(), GlobalConstant.USER_EXAM_DETAIL + orderId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserExamDetail>() {
                    @Override
                    public void onNext(UserExamDetail userExamDetail) {
                        setViews(userExamDetail);
                        hideLoading();
                        refreshExamDetail.setVisibility(View.VISIBLE);
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
}
