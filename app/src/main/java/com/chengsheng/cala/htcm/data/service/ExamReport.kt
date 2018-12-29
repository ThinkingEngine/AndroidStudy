package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.ExamReportList
import com.chengsheng.cala.htcm.protocol.childmodela.ExamReportDetial
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Author: 蔡浪
 * CreateDate: 2018-12-29 9:50
 * Description:体检报告 服务端口
 */
interface ExamReport {

    /**
     * 体检报告列表
     *
     *customerID:String 客户的id
     *
     * 返回：ExamReportList.class
     * */
    @GET(API.ISSUED_REPORT)
    @Headers("Authorization:true")
    fun getIssuedReport(@Query("customer_id") customerID: String): Observable<Response<ExamReportList>>

    /**
     * 报告详情
     *
     *orderID:String 订单的id
     *
     * 返回：ExamReportDetial.class
     * */
    @GET(API.EXAM_RESULT)
    @Headers("Authorization:true")
    fun getExamResult(@Path("orderID") orderID: String): Observable<Response<ExamReportDetial>>
}