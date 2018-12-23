package com.chengsheng.cala.htcm.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.protocol.DoctorProtocol
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author: 任和
 * CreateDate: 2018/12/21 9:39 AM
 * Description: 医生列表
 */
class DoctorAdapter(data: List<DoctorProtocol.ItemsBean>) :
        BaseQuickAdapter<DoctorProtocol.ItemsBean>(R.layout.item_doctor, data) {
    override fun convert(helper: BaseViewHolder, data: DoctorProtocol.ItemsBean) {
        helper.getView<SimpleDraweeView>(R.id.ivDoctorAvatar).setImageURI(data.avatar_path)
        helper.setText(R.id.tvDoctorName, data.name)
        helper.setText(R.id.tvDoctorPosition, data.professional_title)
        helper.setText(R.id.tvDoctorSkill, "擅长:" + data.professional_skill)
    }
}