package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_find_card_pwd_1.*

/**
 * Author: 任和
 * CreateDate: 2018/12/24 2:10 PM
 * Description: 找回卡密码第一步
 */
@SuppressLint("CheckResult")
class FindCardPwdAStep1Activity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_find_card_pwd_1
    }

    override fun initView() {
        //下一步
        RxView.clicks(btnNextStep).subscribe {
            startActivity(FindCardPwdAStep2Activity())
        }
    }

    override fun getData() {

    }
}