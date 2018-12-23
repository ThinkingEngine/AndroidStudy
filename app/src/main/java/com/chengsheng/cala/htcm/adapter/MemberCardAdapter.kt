package com.chengsheng.cala.htcm.adapter

import android.support.constraint.ConstraintLayout
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.widget.MemberCardPopWindow

/**
 * Author: 任和
 * CreateDate: 2018/12/23 3:39 PM
 * Description: 会员卡
 */
class MemberCardAdapter(data: List<Any>) : BaseQuickAdapter<Any>(R.layout.item_member_card, data) {
    override fun convert(helper: BaseViewHolder?, data: Any?) {

        //查看详情
        helper?.getView<ConstraintLayout>(R.id.layoutMemberItem)?.setOnClickListener {

        }

        //显示二维码
        helper?.getView<View>(R.id.viewShowCardPop)?.setOnClickListener {
            MemberCardPopWindow(mContext).showPopupWindow()
        }

    }
}