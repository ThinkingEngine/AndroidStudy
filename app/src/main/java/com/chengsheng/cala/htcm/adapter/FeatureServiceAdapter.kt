package com.chengsheng.cala.htcm.adapter

import android.view.View
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.manager.FrescoManager
import com.chengsheng.cala.htcm.module.find.ServiceDetailActivity
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol
import com.chengsheng.cala.htcm.utils.ActivityUtil
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Author: 任和
 * CreateDate: 2018/12/19 6:16 PM
 * Description: 特色服务列表Adapter
 */
class FeatureServiceAdapter(data: List<RecommendProProtocol.ItemsBean.RecommendBean>) :
        BaseQuickAdapter<RecommendProProtocol.ItemsBean.RecommendBean>(R.layout.item_feature_service, data) {

    override fun convert(helper: BaseViewHolder?, item: RecommendProProtocol.ItemsBean.RecommendBean?) {
        val ivProCover: SimpleDraweeView = helper?.getView(R.id.ivProCover)!!
        val featureDivide: View = helper.getView(R.id.featureDivide)
        val layoutFeatureService: RelativeLayout = helper.getView(R.id.layoutFeatureService)

        FrescoManager.loadRoundImage(mContext, ivProCover, item?.cover_photo, 4f)
        helper.setText(R.id.tvProSaleNum, (item?.current_sales_num).toString() + "人已享受")
        helper.setText(R.id.tvProName, item?.name)
        helper.setText(R.id.tvProDes, item?.intro)
//        helper.setText(R.id.tvProPrice, "¥" + item?.price)

        featureDivide.visibility = if (helper.layoutPosition == itemCount - 1) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }

        //查看详情
        layoutFeatureService.setOnClickListener {
            ActivityUtil.startActivity(mContext, ServiceDetailActivity())
        }
    }
}