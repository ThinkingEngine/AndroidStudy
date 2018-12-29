package com.chengsheng.cala.htcm.module.user

import android.annotation.SuppressLint
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_login_pwd_verification.*

/**
 * Author: 任和
 * CreateDate: 2018/12/28 8:47 PM
 * Description: 修改绑定手机号-密码验证第一步
 */
@SuppressLint("CheckResult")
class PwdVerificationActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login_pwd_verification
    }

    override fun initView() {
        //下一步
        RxView.clicks(btnNextStep).subscribe {
            startActivity(MsmVerificationActivity())
        }

        //通过短信验证
        RxView.clicks(layoutVerificationByMSM).subscribe {
            startActivity(MsmVerificationActivity())
        }
    }

    override fun getData() {

    }
}