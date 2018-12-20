package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.ExamItemsList
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-20 15:19
 * Description:我的体检 服务接口块儿
 */
interface MyExamService {

    /**
     * 我的体检列表
     *
     * mode:请求的数据的模式（全部 all，待体检 体检中 已体检）
     * customer_ids:客户id
     * page：数据页
     * */
    @GET(API.MY_EXAM_LIST)
    @Headers("Authorization:true")
    fun getExamList(@Query("mode") mode: String, @Query("customer_ids") customerIds: String, @Query("page") page: String): Observable<Response<ExamItemsList>>
}