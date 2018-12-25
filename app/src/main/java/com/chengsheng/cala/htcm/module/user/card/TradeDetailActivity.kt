package com.chengsheng.cala.htcm.module.user.card

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.TradeDetailAdapter
import com.chengsheng.cala.htcm.base.BaseRefreshActivity

/**
 * Author: 任和
 * CreateDate: 2018/12/24 2:13 PM
 * Description: 交易记录
 */
class TradeDetailActivity : BaseRefreshActivity<Any>() {

    var data: ArrayList<Any>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_trade_detail
    }

    override fun initViews() {

    }

    override fun getData(page: Int) {
        data = ArrayList()
        data?.add("just")
        data?.add("just")
        fillData(data)
    }

    override fun getCurrentAdapter(): BaseQuickAdapter<Any>? {
        return TradeDetailAdapter(ArrayList())
    }
}