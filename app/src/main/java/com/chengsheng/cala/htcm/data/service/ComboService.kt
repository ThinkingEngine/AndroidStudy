package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.ExamApponitments
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-24 15:08
 * Description:
 */
interface ComboService {

    @GET(API.EXAM_PACKAGE)
    fun getComboInfoFilters(@Query("filters") filters: String, @Query("page") page: String, @Query("sort_fields") sortFields: String): Observable<Response<ExamApponitments>>
}