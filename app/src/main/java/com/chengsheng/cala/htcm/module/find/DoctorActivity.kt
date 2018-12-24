package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.DoctorAdapter
import com.chengsheng.cala.htcm.base.BaseRefreshActivity
import com.chengsheng.cala.htcm.data.repository.OrganizationRepository
import com.chengsheng.cala.htcm.protocol.DoctorProtocol
import kotlinx.android.synthetic.main.activity_doctor.*

/**
 * Author: 任和
 * CreateDate: 2018/12/21 3:50 PM
 * Description: 医生列表
 */
@SuppressLint("CheckResult")
class DoctorActivity : BaseRefreshActivity<DoctorProtocol.ItemsBean>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_doctor
    }

    override fun initViews() {
        titleBar?.setFinishClickListener {
            finish()
        }
    }

    override fun getData(page: Int) {
        OrganizationRepository.default?.getAllDoctor(page)
                ?.subscribe({

                    fillData(it.items)

                }) {
                    showError(it)
                }
    }

    override fun getCurrentAdapter(): BaseQuickAdapter<DoctorProtocol.ItemsBean>? {
        return DoctorAdapter(ArrayList())
    }
}