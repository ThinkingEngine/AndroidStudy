package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.MemberCardDetailProtocol
import com.chengsheng.cala.htcm.protocol.MemberCardProtocol
import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

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
    @GET(API.MEMBER_CARD_DETAIL)
    @Headers("Authorization:true")
    fun getCardDetail(@Path("id") id: Int): Observable<Response<MemberCardDetailProtocol>>

    /**
     * 解绑会员卡
     */
    @HTTP(method = "DELETE", path = API.DELETE_MEMBER_CARD, hasBody = true)
    @Headers("Authorization:true")
    fun deleteMemberCard(@Path("id") id: Int,
                         @Body() password: RequestBody): Observable<Response<Any>>

    /**
     * 发送短信验证码
     */
    @POST(API.SEND_CAPTCHA)
    @Headers("Authorization:true")
    fun sendCaptcha(@Query("mobile") mobile: String): Observable<Response<JsonObject>>

    /**
     * 绑定会员卡
     */
    @POST(API.BIND_MEMBER_CARD)
    @Headers("Authorization:true")
    fun bind(@Query("bind_mobile") bind_mobile: String,
             @Query("card_number") card_number: String,
             @Query("password") password: String,
             @Query("uuid") uuid: String,
             @Query("code") code: String): Observable<Response<JsonObject>>


}