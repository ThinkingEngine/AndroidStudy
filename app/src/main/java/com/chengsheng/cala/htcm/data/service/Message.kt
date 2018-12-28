package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.childmodela.MessageList
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


/**
 * Author: 蔡浪
 * CreateDate: 2018-12-28 14:56
 * Description:消息 服务接口
 */
interface Message {

    /**
     * 消息列表
     *
     * */
    @GET(API.SMS_LIST)
    @Headers("Authorization:true")
    fun getSMSList(@Query("page") page: String): Observable<Response<MessageList>>
}