package com.chengsheng.cala.htcm.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.manager.FrescoManager
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol
import com.facebook.drawee.view.SimpleDraweeView


/**
 * Author: 任和
 * CreateDate: 2018/12/20 10:30 AM
 * Description: 医疗美容列表Adapter
 */
class HealthBeautyAdapter(data: List<RecommendProProtocol.ItemsBean.RecommendBean>) :
        BaseQuickAdapter<RecommendProProtocol.ItemsBean.RecommendBean>(R.layout.item_health_beauty, data) {

    override fun convert(helper: BaseViewHolder?, item: RecommendProProtocol.ItemsBean.RecommendBean?) {
        val ivProCover: SimpleDraweeView = helper?.getView(R.id.ivHealthBeauty)!!
        FrescoManager.loadRoundImage(mContext, ivProCover, item?.banner_photo, 4f)
    }
}