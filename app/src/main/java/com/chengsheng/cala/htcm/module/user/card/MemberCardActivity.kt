package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.MemberCardAdapter
import com.chengsheng.cala.htcm.base.BaseRefreshActivity
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_member_card.*

/**
 * Author: 任和
 * CreateDate: 2018/12/23 4:10 PM
 * Description: 会员卡
 */
@SuppressLint("CheckResult")
class MemberCardActivity : BaseRefreshActivity<Any>() {

    var data: ArrayList<Any>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_member_card
    }

    override fun initViews() {
        //添加卡
        RxView.clicks(layoutAddCard).subscribe {
            startActivity(AddMemCardActivity())
        }
    }

    override fun getData(page: Int) {

    }

    override fun getCurrentAdapter(): BaseQuickAdapter<Any>? {
        data = ArrayList()
        data?.add("just")
        data?.add("just")
        return MemberCardAdapter(this, data!!)
    }
}