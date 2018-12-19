package com.chengsheng.cala.htcm.module.activitys;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
import com.chengsheng.cala.htcm.widget.OnViewClickListener;


/**
 * Author: 蔡浪
 * CreateDate: 2018/12/19 9:14 AM
 * Description:登录详情
 */


public class RegisterDetailActivity extends BaseActivity {

    private AppTitleBar appTitleBar;
    private TextView examStatus;//体检订单的状态
    private TextView examUserName;
    private TextView examAppointmentNumber;
    private TextView examOrderPhone;
    private TextView examSexBox;
    private TextView examOrderAge;
    private TextView examDate;
    private TextView examGroup;
    private TextView examAddress;
    private TextView examComboNameDetail;
    private TextView examItemList;
    private ImageView registerDetailCode;
    private TextView projectTotalVal;
    private TextView hasPayVal;
    private Button loginButtonDetail;
    private SwipeRefreshLayout refreshRegisterDetail;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_detail;
    }

    @Override
    public void initView() {

        appTitleBar = findViewById(R.id.register_detail_title);//
        refreshRegisterDetail = findViewById(R.id.refresh_register_detail);


        appTitleBar.setFinishClickListener(new OnViewClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void getData() {

    }
}
