package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import android.support.v7.widget.GridLayoutManager
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.OrganizationAdapter
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.data.repository.OrganizationRepository
import kotlinx.android.synthetic.main.activity_organization.*

/**
 * Author: 任和
 * CreateDate: 2018/12/21 8:59 AM
 * Description: 科室列表
 */
@SuppressLint("CheckResult")
class OrganizationActivity : BaseActivity() {

    private var organizationAdapter: OrganizationAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_organization
    }

    override fun initView() {
        titleBar?.setFinishClickListener {
            finish()
        }

        organizationAdapter = OrganizationAdapter(this, ArrayList())
        recyclerView?.layoutManager = GridLayoutManager(this, 2)
        recyclerView?.adapter = organizationAdapter
    }

    override fun getData() {
        OrganizationRepository.default?.getOrganization()
                ?.subscribe({

                    organizationAdapter?.setNewData(it.items)

                }) {
                    showError(it)
                }
    }
}