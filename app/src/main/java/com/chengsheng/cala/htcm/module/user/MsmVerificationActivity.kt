package com.chengsheng.cala.htcm.module.user

import android.annotation.SuppressLint
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_msm_verification.*

/**
 * Author: 任和
 * CreateDate: 2018/12/28 9:01 PM
 * Description: 短信验证
 */
@SuppressLint("CheckResult")
class MsmVerificationActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_msm_verification
    }

    override fun initView() {
        //
        RxView.clicks(btnNextStep).subscribe {
            startActivity(ChangeMobileActivity())
        }
    }

    override fun getData() {

    }
}