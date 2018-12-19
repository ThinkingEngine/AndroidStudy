package com.chengsheng.cala.htcm.module.activitys;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.widget.AppTitleBar;
//import com.chengsheng.cala.htcm.widget.OnViewClickListener;


/**
 * Author: 蔡浪
 * CreateDate: 2018/12/19 9:14 AM
 * Description:登录详情
 */


public class RegisterDetailActivity extends BaseActivity {

    private AppTitleBar appTitleBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_detail;
    }

    @Override
    public void initView() {

        appTitleBar = findViewById(R.id.register_detail_title);



    }

    @Override
    public void getData() {

    }
}
