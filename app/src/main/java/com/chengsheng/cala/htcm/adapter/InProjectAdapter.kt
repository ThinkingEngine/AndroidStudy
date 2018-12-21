package com.chengsheng.cala.htcm.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.protocol.OrganizationProtocol

/**
 * Author: 任和
 * CreateDate: 2018/12/21 9:39 AM
 * Description: 科室开展项目
 */
class InProjectAdapter(data: List<OrganizationProtocol.ItemsBean.ExamItemsBean>) :
        BaseQuickAdapter<OrganizationProtocol.ItemsBean.ExamItemsBean>(R.layout.item_in_project, data) {
    override fun convert(helper: BaseViewHolder?, data: OrganizationProtocol.ItemsBean.ExamItemsBean?) {
        helper?.setText(R.id.tvProName, data?.name)
        helper?.setText(R.id.tvProIntroduce, data?.intro)
    }
}