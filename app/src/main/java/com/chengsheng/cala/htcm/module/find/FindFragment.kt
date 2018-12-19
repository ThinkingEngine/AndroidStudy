package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.RecommendProAdapter
import com.chengsheng.cala.htcm.base.BaseFragment
import com.chengsheng.cala.htcm.data.repository.ProjectRepository
import kotlinx.android.synthetic.main.fragment_find.*
import kotlinx.android.synthetic.main.merge_find_feature_service.*

/**
 * Author: 任和
 * CreateDate: 2018/12/19 4:36 PM
 * Description: 发现
 */
@SuppressLint("CheckResult")
class FindFragment : BaseFragment() {

    private var proAdapter: RecommendProAdapter? = null

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
        proAdapter = RecommendProAdapter(ArrayList())
        rvFeatureService?.adapter = proAdapter

        swipeRefresh?.setOnRefreshListener {
            getData()
        }
    }

    override fun getData() {
        ProjectRepository.Companion.default?.getRecommendPro()
                ?.subscribe({

                    swipeRefresh?.isRefreshing = false
                    proAdapter?.setNewData(it.items)

                }) {
                    showError(it)
                }
    }
}