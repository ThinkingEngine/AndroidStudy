package com.chengsheng.cala.htcm.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.protocol.DepositValueProtocol

/**
 * Author: 任和
 * CreateDate: 2018/12/26 11:10 AM
 * Description: 充值金额
 */
class DepositValueAdapter(data: List<DepositValueProtocol>, private var selectListener: OnSelectListener) : BaseQuickAdapter<DepositValueProtocol>(
        R.layout.item_deposit_value, data) {
    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, data: DepositValueProtocol) {
        val tvValue = helper.getView<TextView>(R.id.tvDepositValue)

        //value为0表示手动输入其他金额
        tvValue.text = if (data.value == 0) {
            "其他金额"
        } else {
            data.value.toString() + "元"
        }

        tvValue.background = if (data.isSelected) {
            if (data.value == 0) {
                mContext.getDrawable(R.mipmap.qitajine_xuanzhong)
            } else {
                mContext.getDrawable(R.mipmap.jine_xuanzhong)
            }
        } else {
            if (data.value == 0) {
                mContext.getDrawable(R.mipmap.qitajine)
            } else {
                mContext.getDrawable(R.mipmap.jine)
            }
        }

        tvValue.setOnClickListener {
            selectListener.onSelected(helper.layoutPosition, data)
        }
    }

    interface OnSelectListener {
        fun onSelected(position: Int, data: DepositValueProtocol)
    }
}