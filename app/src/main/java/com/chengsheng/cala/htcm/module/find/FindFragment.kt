package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.FeatureServiceAdapter
import com.chengsheng.cala.htcm.adapter.HealthBeautyAdapter
import com.chengsheng.cala.htcm.base.BaseFragment
import com.chengsheng.cala.htcm.data.repository.ProjectRepository
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.merge_find_feature_service.*
import kotlinx.android.synthetic.main.merge_find_medical_beauty.*

/**
 * Author: 任和
 * CreateDate: 2018/12/19 4:36 PM
 * Description: 发现
 */
@SuppressLint("CheckResult")
class FindFragment : BaseFragment() {

    //特色服务Adapter
    private var featureServiceAdapter: FeatureServiceAdapter? = null
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

    override fun getLayoutId(): Int {
        return R.layout.fragment_find
    }

    override fun initView(view: View?) {
        healthBeautyData = ArrayList()
        featureData = ArrayList()

        //特色服务
        rvFeatureService?.layoutManager = LinearLayoutManager(context)
        rvFeatureService?.isNestedScrollingEnabled = false
        featureServiceAdapter = FeatureServiceAdapter(ArrayList())
        rvFeatureService?.adapter = featureServiceAdapter

        //医疗美容
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvHealthBeauty?.layoutManager = layoutManager
        rvHealthBeauty?.isNestedScrollingEnabled = false
        healthBeautyAdapter = HealthBeautyAdapter(ArrayList())
        rvHealthBeauty?.adapter = healthBeautyAdapter

        swipeRefresh?.setOnRefreshListener {
            getData()
        }

        //跳转到医疗美容页
        RxView.clicks(layoutHealthBeauty).subscribe {
            showShortToast("点我干嘛")
        }

        //跳转到特色服务页
        RxView.clicks(layoutFeatureService).subscribe {
            startActivity(FeatureServiceActivity())
        }
    }

    override fun getData() {
        ProjectRepository.Companion.default?.getRecommendPro()
                ?.subscribe({

                    swipeRefresh?.isRefreshing = false
                    healthBeautyData?.clear()
                    featureData?.clear()

                    for (item in it.items) {
                        if (item.id == 1) {
                            featureData?.addAll(item.recommend)
                            featureData?.addAll(item.recommend)
                            featureData?.addAll(item.recommend)
                            featureData?.addAll(item.recommend)
                            featureData?.addAll(item.recommend)
                        }
                        if (item.id == 2) {
                            healthBeautyData?.addAll(item.recommend)
                            healthBeautyData?.addAll(item.recommend)
                        }
                    }

                    featureServiceAdapter?.setNewData(featureData)
                    healthBeautyAdapter?.setNewData(healthBeautyData)

                }) {
                    showError(it)
                }
    }
}