package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.AssistantList
import com.chengsheng.cala.htcm.protocol.childmodelb.IntelligentCheck
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Author: 蔡浪
 * CreateDate: 2018-12-28 10:24
 * Description:智能助理 接口服务
 */
interface AIAssistantService {

    /**
     * 智能助理
     *
     * excludeClosed:String ?
     * page:String 数据页数
     *
     * 返回类型:AssistantList.class
     * */
    @GET(API.AI)
    @Headers("Authorization:true")
    fun getAIAssistants(@Query("exclude_closed") excludeClosed: String, @Query("page") page: String): Observable<Response<AssistantList>>

    /**
     * 智能导检
     *
     * orderId:String ?
     *
     *
     * 返回类型:IntelligentCheck.class
     * */
    @GET(API.AI_CHECK)
    @Headers("Authorization:true")
    fun aiCheck(@Path("orderId")orderId:String):Observable<Response<IntelligentCheck>>
}