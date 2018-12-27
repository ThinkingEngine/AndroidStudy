package com.chengsheng.cala.htcm.module.user.card

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.TradeDetailAdapter
import com.chengsheng.cala.htcm.base.BaseRefreshActivity
import com.chengsheng.cala.htcm.data.repository.MemberCardRepository
import com.chengsheng.cala.htcm.protocol.TradeRecordProtocol

/**
 * Author: 任和
 * CreateDate: 2018/12/24 2:13 PM
 * Description: 交易记录
 */
class TradeRecordActivity : BaseRefreshActivity<TradeRecordProtocol.ItemsBean>() {

    var data: ArrayList<Any>? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_trade_detail
    }

    override fun initViews() {

    }

    @SuppressLint("CheckResult")
    override fun getData(page: Int) {
        val id = intent.getIntExtra("id", 0)
        MemberCardRepository.default?.getTradeRecord(id)
                ?.subscribe({

                    fillData(it.items)

                }) {
                    showError(it)
                    fillData(ArrayList())
                }
    }

    override fun getCurrentAdapter(): BaseQuickAdapter<TradeRecordProtocol.ItemsBean>? {
        return TradeDetailAdapter(ArrayList())
    }
}