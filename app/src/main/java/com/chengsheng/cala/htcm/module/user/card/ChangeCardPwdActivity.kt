package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_change_card_pwd.*

/**
 * Author: 任和
 * CreateDate: 2018/12/24 2:10 PM
 * Description: 修改卡密码
 */
@SuppressLint("CheckResult")
class ChangeCardPwdActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_change_card_pwd
    }

    override fun initView() {
        //忘记卡密码
        RxView.clicks(tvForgetPwd).subscribe {
            startActivity(FindCardPwdAStep1Activity())
        }
    }

    override fun getData() {

    }
}