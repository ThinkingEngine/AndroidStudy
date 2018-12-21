package com.chengsheng.cala.htcm.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.protocol.DoctorProtocol

/**
 * Author: 任和
 * CreateDate: 2018/12/21 9:39 AM
 * Description: 科室开展项目
 */
class DoctorAdapter(data: List<DoctorProtocol.ItemsBean>) :
        BaseQuickAdapter<DoctorProtocol.ItemsBean>(R.layout.item_in_project, data) {
    override fun convert(helper: BaseViewHolder?, data: DoctorProtocol.ItemsBean?) {

    }
}