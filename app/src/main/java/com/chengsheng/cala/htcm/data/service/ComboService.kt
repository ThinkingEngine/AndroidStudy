package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.AppointmentDetail
import com.chengsheng.cala.htcm.protocol.ExamApponitments
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-24 15:08
 * Description:套餐
 */
interface ComboService {

    /**
     * 套餐列表启用的
     *
     *  filters: String
     *  page: String
     *  sortFields: String
     * */
    @GET(API.EXAM_PACKAGE)
    fun getComboInfoFilters(@Query("filters") filters: String, @Query("page") page: String, @Query("sort_fields") sortFields: String): Observable<Response<ExamApponitments>>

    /**
     * 套餐详情
     *
     * comboId: String 套餐的id
     *
     *返回 AppointmentDetail.class
     * */
    @GET(API.EXAM_PACKAGES)
    @Headers("Authorization:true")
    fun getComboDetail(@Path("comboID") comboId: String): Observable<Response<AppointmentDetail>>
}