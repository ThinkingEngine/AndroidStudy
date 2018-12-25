package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.MemberCardProtocol
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Author: 任和
 * CreateDate: 2018/12/15 8:50 AM
 * Description: 会员卡模块相关接口
 */
interface MemberCardService {

    /**
     * 会员卡列表
     */
    @GET(API.MEMBER_CARD_LIST)
    @Headers("Authorization:true")
    fun getCardList(): Observable<Response<MemberCardProtocol>>

    /**
     * 会员卡详情
     */
    @GET(API.MEMBER_CARD_LIST)
    @Headers("Authorization:true")
    fun getCardDetail(): Observable<Response<Any>>

}