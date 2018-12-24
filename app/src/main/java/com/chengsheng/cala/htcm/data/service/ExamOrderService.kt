package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.childmodelb.UserExamDetail
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-19 14:14
 * Description:体检订单 类服务
 */
interface ExamOrderService {

    /**
     *  订单详情
     */
    @GET(API.USER_EXAM_DETAIL + "{orderID}")
    @Headers("Authorization:true")
    fun getUserExamDetail(@Path("orderID") orderID: String): Observable<Response<UserExamDetail>>

    /**
     *  自助登记
     */
    @POST(API.USER_REGISTER + "{orderID}")
    @Headers("Authorization:true")
    fun registration(@Path("orderID") orderID: String): Observable<Response<ResponseBody>>

}