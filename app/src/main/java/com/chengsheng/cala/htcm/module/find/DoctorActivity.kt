package com.chengsheng.cala.htcm.module.find

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chengsheng.cala.htcm.R
import com.chengsheng.cala.htcm.adapter.DoctorAdapter
import com.chengsheng.cala.htcm.base.BaseRefreshActivity
import com.chengsheng.cala.htcm.data.repository.OrganizationRepository

/**
 * Author: 任和
 * CreateDate: 2018/12/21 3:50 PM
 * Description: 医生列表
 */
@SuppressLint("CheckResult")
class DoctorActivity : BaseRefreshActivity<Any>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_doctor
    }

    override fun getData(page: Int) {
        OrganizationRepository.default?.getAllDoctor()
                ?.subscribe({

                    fillData(ArrayList())

                }) {

                }
    }

    override fun getCurrentAdapter(): BaseQuickAdapter<Any>? {
        return DoctorAdapter(ArrayList())
    }
}