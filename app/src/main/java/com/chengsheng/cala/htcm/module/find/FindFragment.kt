package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.FeatureServiceAdapter
import com.chengsheng.cala.htcm.base.BaseFragment
import com.chengsheng.cala.htcm.data.repository.ProjectRepository
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.merge_find_feature_service.*

/**
 * Author: 任和
 * CreateDate: 2018/12/19 4:36 PM
 * Description: 发现
 */
@SuppressLint("CheckResult")
class FindFragment : BaseFragment() {

    //特色服务Adapter
    private var proAdapter: FeatureServiceAdapter? = null
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
        rvFeatureService?.layoutManager = LinearLayoutManager(context)
        rvFeatureService?.isNestedScrollingEnabled = false
        proAdapter = FeatureServiceAdapter(ArrayList())
        rvFeatureService?.adapter = proAdapter

        healthBeautyData = ArrayList()
        featureData = ArrayList()

        swipeRefresh?.setOnRefreshListener {
            getData()
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
                        }
                        if (item.id == 2) {
                            healthBeautyData?.addAll(item.recommend)
                        }
                    }

                    proAdapter?.setNewData(featureData)

                }) {
                    showError(it)
                }
    }
}