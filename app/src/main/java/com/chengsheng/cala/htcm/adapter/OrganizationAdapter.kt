package com.chengsheng.cala.htcm.adapter

import android.os.Bundle
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.module.find.OrganizationDetailActivity
import com.chengsheng.cala.htcm.protocol.OrganizationProtocol
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author: 任和
 * CreateDate: 2018/12/21 9:39 AM
 * Description: 部门科室
 */
class OrganizationAdapter(private var v1: BaseActivity, data: List<OrganizationProtocol.ItemsBean>) :
        BaseQuickAdapter<OrganizationProtocol.ItemsBean>(R.layout.item_organization, data) {

    override fun convert(helper: BaseViewHolder?, data: OrganizationProtocol.ItemsBean?) {
        val ivOrganization: SimpleDraweeView? = helper?.getView(R.id.ivOrganization)
        ivOrganization?.setImageURI(data?.photo_path)
        helper?.setText(R.id.tvOrganizationName, data?.name)
        helper?.setText(R.id.tvOrganizationDesc, data?.intro)

        //查看机构详情
        helper?.getView<LinearLayout>(R.id.organizationItemView)?.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("data", data)
            v1.startActivity(OrganizationDetailActivity(), bundle)
        }
    }
}