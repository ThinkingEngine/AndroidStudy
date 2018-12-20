package com.chengsheng.cala.htcm.module.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.adapter.ExamItemExpandableListViewAdapter;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.data.repository.ExamOrderRepository;
import com.chengsheng.cala.htcm.protocol.FamiliesListItem;
import com.chengsheng.cala.htcm.protocol.childmodelb.UserExamDetail;
import com.chengsheng.cala.htcm.utils.ActivityUtil;
import com.chengsheng.cala.htcm.utils.UserUtil;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
import com.chengsheng.cala.htcm.widget.MyExpandableListView;
import com.chengsheng.cala.htcm.widget.OnViewClickListener;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;


/**
 * Author: 蔡浪
 * CreateDate: 2018/12/19 9:14 AM
 * Description:登录详情
 */


public class RegisterDetailActivity extends BaseActivity {

    private AppTitleBar appTitleBar;
    private TextView examStatus;//体检订单的状态
    private TextView examUserName;
    private TextView numberMark;
    private TextView examAppointmentNumber;
    private TextView examOrderPhone;
    private TextView examSexBox;
    private TextView examOrderAge;
    private TextView examDate;
    private TextView examGroup;
    private TextView examAddress;
    private TextView examComboNameDetail;
    private MyExpandableListView examItemList;
    private ImageView registerDetailCode;
    private TextView projectTotalVal;
    private TextView hasPayVal;
    private Button loginButtonDetail;
    private SwipeRefreshLayout refreshRegisterDetail;

    private String orderId = "";
    private ZLoadingDialog loadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_detail;
    }

    @Override
    public void initView() {
        /**
         * 获取上个界面传来的订单ID
         * */
        Bundle bundle = getIntent().getExtras();
        orderId = bundle.getString("ORDER_ID");

        /**
         * 初始化动画模组
         * */
        loadingDialog = new ZLoadingDialog(this);
        loadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);
        loadingDialog.setHintText("加载中.....");
        loadingDialog.setLoadingColor(getResources().getColor(R.color.colorPrimary));
        loadingDialog.setDialogBackgroundColor(getResources().getColor(R.color.colorText));
        loadingDialog.setHintTextColor(getResources().getColor(R.color.colorPrimary));

        /**
         * UI元素关联
         * */
        initViews();


        appTitleBar.setFinishClickListener(new OnViewClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void getData() {
        updateData(false);
    }

    /*
     * 根据网络请求，刷新页面
     * **/
    private void setViews(final UserExamDetail data) {

        String stats = data.getExam_status();
        //待体检
        if (stats.equals(GlobalConstant.RESERVATION)) {
            examStatus.setText("待体检");
            numberMark.setText("预约号：");
        } else if (stats.equals(GlobalConstant.CHECKING)) {  //体检中
            examStatus.setText("体检中");
            numberMark.setText("体检号：");
        } else { //已体检
            examStatus.setText("已体检");
            numberMark.setText("体检号：");
        }
        examUserName.setText(data.getName());
        examAppointmentNumber.setText(data.getCustomer().getReservation_or_registration().getId());
        examOrderPhone.setText(data.getCustomer().getMobile());
        examDate.setText(data.getCustomer().getReservation_or_registration().getDate());
        examOrderAge.setText(data.getCustomer().getAge());
        examGroup.setText(data.getOrganization().getName());
        examAddress.setText(data.getOrganization().getAddress());

        examComboNameDetail.setText(data.getName());


        ExamItemExpandableListViewAdapter adapter = new ExamItemExpandableListViewAdapter(this, data.getExam_item(),GlobalConstant.COMBO_DETAIL_MARK);
        examItemList.setFocusable(false);
        examItemList.setAdapter(adapter);
        examItemList.setIndicatorBounds(examItemList.getWidth() - 140, examItemList.getWidth() - 10);

        if (data.isCan_autonomous()) {
            loginButtonDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registration();
                }
            });
        } else {
            loginButtonDetail.setVisibility(View.INVISIBLE);
        }

        if (data.getCustomer().getSex().equals("male")) {
            examSexBox.setText("男");
        } else {
            examSexBox.setText("女");
        }

        refreshRegisterDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData(true);
            }
        });

        registerDetailCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamiliesListItem familiesListItem = new FamiliesListItem();
                familiesListItem.setHealth_card_no(data.getCustomer().getReservation_or_registration().getId());
                familiesListItem.setAvatar_path(data.getCustomer().getAvatar());
                Bundle bundle = new Bundle();
                bundle.putSerializable("FAMILIES_INFO",familiesListItem);
                ActivityUtil.Companion.startActivity(RegisterDetailActivity.this,new UserCardActivity(),bundle);
            }
        });

    }

    private void initViews() {
        appTitleBar = findViewById(R.id.register_detail_title);
        refreshRegisterDetail = findViewById(R.id.refresh_register_detail);
        examStatus = findViewById(R.id.exam_status);
        examUserName = findViewById(R.id.exam_user_name);
        numberMark = findViewById(R.id.number_mark);
        examAppointmentNumber = findViewById(R.id.exam_appointment_number);
        examOrderPhone = findViewById(R.id.exam_order_phone);
        examSexBox = findViewById(R.id.exam_order_sex);
        examOrderAge = findViewById(R.id.exam_order_age);
        examDate = findViewById(R.id.exam_date);
        examGroup = findViewById(R.id.exam_group);
        examAddress = findViewById(R.id.exam_address);
        examComboNameDetail = findViewById(R.id.exam_combo_name_detail);
        examItemList = findViewById(R.id.exam_item_list);
        registerDetailCode = findViewById(R.id.register_detail_code);
        projectTotalVal = findViewById(R.id.project_total_val);
        hasPayVal = findViewById(R.id.has_pay_val);
        loginButtonDetail = findViewById(R.id.login_button_detail);

    }

    /**
     * 更新页面数据。
     */
    private void updateData(final boolean isRefresh) {
        if (!UserUtil.isLogin()) {
            return;
        }

        if (!isRefresh) {
            loadingDialog.show();
        }
        ExamOrderRepository.Companion.getDefault().getUserExamDetail(orderId).subscribe(new DisposableObserver<UserExamDetail>() {
            @Override
            public void onNext(UserExamDetail userExamDetail) {
                setViews(userExamDetail);
                if (isRefresh) {
                    refreshRegisterDetail.setRefreshing(false);
                } else {
                    loadingDialog.cancel();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isRefresh) {
                    refreshRegisterDetail.setRefreshing(false);
                } else {
                    loadingDialog.cancel();
                }
                showError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 进行登记
     */
    private void registration() {
        if (!UserUtil.isLogin()) {
            return;
        }

        loadingDialog.show();

        ExamOrderRepository.Companion.getDefault().registration(orderId).subscribe(new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                loadingDialog.cancel();
                showShortToast("您已登记成功");
                finish();
            }

            @Override
            public void onError(Throwable e) {
                loadingDialog.cancel();
                showError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }

}
