package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.DoctorProtocol
import com.chengsheng.cala.htcm.protocol.OrganizationProtocol
import io.reactivex.Observable

/**
 * Author: 任和
 * CreateDate: 2018/12/21 8:50 AM
 * Description: 机构模块相关接口
 */
class OrganizationRepository private constructor() {

    companion object {
        @Volatile
        private var instance: OrganizationRepository? = null

        val default: OrganizationRepository?
            get() {
                if (instance == null) {
                    synchronized(OrganizationRepository::class.java) {
                        if (instance == null) {
                            instance = OrganizationRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 科室列表
     */
    fun getOrganization(): Observable<OrganizationProtocol>? {
        return transformProto(RetrofitHelper.getInstance().organizationService.getOrganization())
    }

    /**
     * 机构详情
     */
    fun getOrganizationDetail(): Observable<Any>? {
        return transformProto(RetrofitHelper.getInstance().organizationService.getOrganizationDetail())
    }

    /**
     * 医生列表
     */
    fun getAllDoctor(page: Int): Observable<DoctorProtocol>? {
        return transformProto(RetrofitHelper.getInstance().organizationService.getAllDoctor(page))
    }

}