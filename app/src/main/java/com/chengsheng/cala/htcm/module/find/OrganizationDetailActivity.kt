package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.InProjectAdapter
import com.chengsheng.cala.htcm.base.BaseActivity
import com.chengsheng.cala.htcm.protocol.OrganizationProtocol
import kotlinx.android.synthetic.main.activity_organization_detail.*

/**
 * Author: 任和
 * CreateDate: 2018/12/21 8:59 AM
 * Description: 机构详情
 */
@SuppressLint("CheckResult")
class OrganizationDetailActivity : BaseActivity() {

    private var inProjectAdapter: InProjectAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_organization_detail
    }

    override fun initView() {
        inProjectAdapter = InProjectAdapter(ArrayList())
        rvProject?.layoutManager = LinearLayoutManager(this)
        rvProject?.adapter = inProjectAdapter
    }

    override fun getData() {
        val data: OrganizationProtocol.ItemsBean = intent.getParcelableExtra("data")
        titleBar?.setTitle(data.name)
                ?.setFinishClickListener {
                    finish()
                }

        ivCover?.setImageURI(data.photo_path)
        tvName?.text = data.name
        tvIntroduction?.text = data.intro
        if (data.exam_items != null && data.exam_items.size > 0) {
            inProjectAdapter?.setNewData(data.exam_items.reversed())
        }
    }
}