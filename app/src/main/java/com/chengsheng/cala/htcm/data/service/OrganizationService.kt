package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.DoctorProtocol
import com.chengsheng.cala.htcm.protocol.OrganizationProtocol
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: 任和
 * CreateDate: 2018/12/21 8:50 AM
 * Description: 机构模块相关接口
 */
interface OrganizationService {

    /**
     * 部门科室
     */
    @GET(API.ORGANIZATION)
    fun getOrganization(): Observable<Response<OrganizationProtocol>>

    /**
     * 机构详情
     */
    @GET(API.ORGANIZATION_DETAIL)
    fun getOrganizationDetail(): Observable<Response<Any>>

    /**
     * 医生
     */
    @GET(API.DOCTORS)
    fun getAllDoctor(@Query("page") page: Int): Observable<Response<DoctorProtocol>>

}