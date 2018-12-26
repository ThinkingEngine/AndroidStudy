package com.chengsheng.cala.htcm.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.protocol.TradeRecordProtocol

/**
 * Author: 任和
 * CreateDate: 2018/12/24 3:04 PM
 * Description: 交易记录
 */
class TradeDetailAdapter(data: List<TradeRecordProtocol.ItemsBean>) :
        BaseQuickAdapter<TradeRecordProtocol.ItemsBean>(R.layout.item_trade_detail, data) {
    override fun convert(helper: BaseViewHolder, data: TradeRecordProtocol.ItemsBean) {

        //statu 1-充值 2-消费
//        1: '线下充值'
//        2: '线上充值'
//        3: '体检'
//        4: '特色服务'
//        5: '医疗美容'

        val tradeName: String = when {
            data.change_type == 1 -> "线下充值"
            data.change_type == 2 -> "线上充值"
            data.change_type == 3 -> "体检"
            data.change_type == 4 -> "特色服务"
            data.change_type == 5 -> "医疗美容"
            else -> "交易"
        }

        val tvTradeAmount: TextView = helper.getView(R.id.tvTradeAmount)

        if (data.status == 1) {
            tvTradeAmount.setTextColor(Color.parseColor("#FFFFAF00"))
            tvTradeAmount.text = "+" + data.change_money
        } else {
            tvTradeAmount.setTextColor(Color.parseColor("#333333"))
            tvTradeAmount.text = "-" + data.change_money
        }

        helper.setText(R.id.tvTradeName, tradeName)
        helper.setText(R.id.tvTradeTime, data.change_date)
    }
}