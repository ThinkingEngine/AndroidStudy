package com.chengsheng.cala.htcm.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol

/**
 * Author: 任和
 * CreateDate: 2018/12/19 6:16 PM
 * Description: 发现页特色服务列表Adapter
 */
class RecommendProAdapter(data: List<RecommendProProtocol.ItemsBean>) : BaseQuickAdapter<RecommendProProtocol.ItemsBean>(
        R.layout.item_feature_service, data) {

    override fun convert(helper: BaseViewHolder?, item: RecommendProProtocol.ItemsBean?) {
        var ivProCover: ImageView = helper?.getView(R.id.ivProCover)!!

        helper.setText(R.id.tvProName, item?.name)
    }
}