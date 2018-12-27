package com.chengsheng.cala.htcm.adapter

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.module.user.card.MemberCardDetailActivity
import com.chengsheng.cala.htcm.protocol.MemberCardProtocol
import com.chengsheng.cala.htcm.utils.StringUtils
import com.chengsheng.cala.htcm.widget.MemberCardPopWindow

/**
 * Author: 任和
 * CreateDate: 2018/12/23 3:39 PM
 * Description: 会员卡
 */
class MemberCardAdapter(private var activity: BaseActivity, data: List<MemberCardProtocol.ItemsBean>) :
        BaseQuickAdapter<MemberCardProtocol.ItemsBean>(R.layout.item_member_card, data) {
    override fun convert(helper: BaseViewHolder?, data: MemberCardProtocol.ItemsBean?) {
        helper?.setText(R.id.tvCardId, StringUtils.addBlank(data?.card_number))
        helper?.setText(R.id.tvAvailable, data?.balance)
        try {
            helper?.setText(R.id.tvBindDate, "绑定日期:" + data?.bind_at)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //查看详情
        helper?.getView<ConstraintLayout>(R.id.layoutMemberItem)?.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", data?.id!!)
            activity.startActivity(MemberCardDetailActivity(), bundle)
        }

        //显示二维码
        helper?.getView<View>(R.id.viewShowCardPop)?.setOnClickListener {
            MemberCardPopWindow(mContext, data?.card_number).showPopupWindow()
        }
    }
}