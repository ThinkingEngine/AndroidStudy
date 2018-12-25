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
                         @Body() password: RequestBody): Observable<Response<JsonObject>>

    /**
     * 发送短信验证码
     */
    @POST(API.SEND_CAPTCHA)
    @Headers("Authorization:true")
    fun sendCaptcha(@Query("mobile") mobile: String): Observable<Response<JsonObject>>

}