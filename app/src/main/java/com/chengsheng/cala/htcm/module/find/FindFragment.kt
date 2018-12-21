package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.FeatureServiceAdapter
import com.chengsheng.cala.htcm.adapter.HealthBeautyAdapter
import com.chengsheng.cala.htcm.base.BaseRefreshFragment
import com.chengsheng.cala.htcm.module.LoadBigPictureActivity
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.merge_find_feature_service.*
import kotlinx.android.synthetic.main.merge_find_grid_view.*
import kotlinx.android.synthetic.main.merge_find_medical_beauty.*

/**
 * Author: 任和
 * CreateDate: 2018/12/19 4:36 PM
 * Description: 发现
 */
@SuppressLint("CheckResult")
class FindFragment : BaseRefreshFragment<RecommendProProtocol.ItemsBean.RecommendBean>() {

    private var tempPicUrl = "http://api.peis-mobile.zz-tech.com.cn:85/storage/banner/5159f338-5349-40f0-beaf-fabb4cce2281.png"

    //医疗美容Adapter
    private var healthBeautyAdapter: HealthBeautyAdapter? = null
    //医疗美容
    private var healthBeautyData: ArrayList<RecommendProProtocol.ItemsBean.RecommendBean>? = null
    //特色服务
    private var featureData: ArrayList<RecommendProProtocol.ItemsBean.RecommendBean>? = null

    companion object {
        fun newInstance(): FindFragment {
            return FindFragment()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_find
    }

    override fun initViews(view: View?) {
        healthBeautyData = ArrayList()
        featureData = ArrayList()

        swipeRefresh?.isEnabled = false

        //特色服务
        recyclerView?.isNestedScrollingEnabled = false

        //医疗美容
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvHealthBeauty?.layoutManager = layoutManager
        rvHealthBeauty?.isNestedScrollingEnabled = false
        healthBeautyAdapter = HealthBeautyAdapter(ArrayList())
        rvHealthBeauty?.adapter = healthBeautyAdapter

        //机构详情
        RxView.clicks(tvOrganizationDetail).subscribe {
            LoadBigPictureActivity.start(context, "机构详情", tempPicUrl)
        }

        //部门科室
        RxView.clicks(tvDepartment).subscribe {
            startActivity(OrganizationActivity())
        }

        //专家队伍
        RxView.clicks(tvExpert).subscribe {
            startActivity(DoctorActivity())
        }

        //跳转到医疗美容页
        RxView.clicks(layoutHealthBeauty).subscribe {
            showShortToast("点我干嘛")
        }

        //跳转到特色服务页
        RxView.clicks(layoutFeatureService)
//                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe {
                    startActivity(FeatureServiceActivity())
                }
    }

    override fun getData(page: Int) {
//        ProjectRepository.Companion.default?.getRecommendPro()
//                ?.subscribe({
//
//                    swipeRefresh?.isRefreshing = false
//                    healthBeautyData?.clear()
//                    featureData?.clear()
//
//                    for (item in it.items) {
//                        if (item.id == 1) {
//                            featureData?.addAll(item.recommend)
//                            featureData?.addAll(item.recommend)
//                            featureData?.addAll(item.recommend)
//                        }
//                        if (item.id == 2) {
//                            healthBeautyData?.addAll(item.recommend)
//                            healthBeautyData?.addAll(item.recommend)
//                        }
//                    }
//
//                    healthBeautyAdapter?.setNewData(healthBeautyData)
//
//                    fillData(featureData)
//
//                }) {
//                    showError(it)
//                }
    }

    override fun getCurrentAdapter(): BaseQuickAdapter<RecommendProProtocol.ItemsBean.RecommendBean>? {
        return FeatureServiceAdapter(ArrayList())
    }
}