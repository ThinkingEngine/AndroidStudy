package com.chengsheng.cala.htcm.module.user

import android.annotation.SuppressLint
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import kotlinx.android.synthetic.main.activity_member_card.*

/**
 * Author: 任和
 * CreateDate: 2018/12/23 5:40 PM
 * Description: 会员卡详情
 */
@SuppressLint("CheckResult")
class MemberCardDetailActivity : BaseActivity() {

    override fun initView() {
        titleBar?.setFinishClickListener {
            finish()
        }
    }

    override fun getData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_member_card_detail
    }
}