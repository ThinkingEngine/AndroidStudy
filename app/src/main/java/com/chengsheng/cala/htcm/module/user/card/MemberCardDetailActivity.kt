package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_member_card_detail.*

/**
 * Author: 任和
 * CreateDate: 2018/12/23 5:40 PM
 * Description: 会员卡详情
 */
@SuppressLint("CheckResult")
class MemberCardDetailActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_member_card_detail
    }

    override fun initView() {
        //修改卡密码
        RxView.clicks(layoutChangePassword).subscribe {
            startActivity(ChangeCardPwdActivity())
        }

        //交易记录
        RxView.clicks(layoutTradeDetail).subscribe {
            startActivity(TradeDetailActivity())
        }

        //充值
        RxView.clicks(btnDeposit).subscribe {
            startActivity(DepositActivity())
        }
    }

    override fun getData() {

    }
}