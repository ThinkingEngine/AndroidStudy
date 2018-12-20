package com.chengsheng.cala.htcm.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author: 任和
 * CreateDate: 2018/12/19 6:16 PM
 * Description: 发现页特色服务列表Adapter
 */
class FeatureServiceAdapter(data: List<RecommendProProtocol.ItemsBean.RecommendBean>) : BaseQuickAdapter<RecommendProProtocol.ItemsBean.RecommendBean>(
        R.layout.item_feature_service, data) {

    override fun convert(helper: BaseViewHolder?, item: RecommendProProtocol.ItemsBean.RecommendBean?) {
        val ivProCover: SimpleDraweeView = helper?.getView(R.id.ivProCover)!!
        ivProCover.setImageURI(item?.cover_photo)
        helper.setText(R.id.tvProName, item?.name)
        helper.setText(R.id.tvProDes, item?.intro)
        helper.setText(R.id.tvProSaleNum, (item?.current_sales_num).toString() + "人已享受")
//        helper.setText(R.id.tvProPrice, "¥" + item?.price)
    }
}