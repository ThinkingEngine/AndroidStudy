package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.childmodela.UserInfo
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: 任和
 * CreateDate: 2018/12/19 10:18 AM
 * Description: 用户模块相关接口
 */
interface UserService {

    /**
     * 获取用户信息
     */
    @GET(API.USER_INFO_URL)
    @Headers("Authorization:true")
    fun getUserInfo(): Observable<Response<UserInfo>>

    /**
     * 修改用户信息
     */
    @PUT(API.UPDATE_USER_INFO)
    @Headers("Authorization:true")
    @FormUrlEncoded
    fun updateUserInfo(@FieldMap map: Map<String, String>): Observable<Response<Any>>

    /**
     * 发送短信验证码
     */
    @POST(API.SEND_ACCOUNT_CAPTCHA)
    @Headers("Authorization:true")
    @FormUrlEncoded
    fun sendCaptcha(@FieldMap map: Map<String, String>): Observable<Response<JsonObject>>

    /**
     * 验证短信验证码
     */
    @PUT(API.VERIFICATION_ACCOUNT_CAPTCHA)
    @Headers("Authorization:true")
    @FormUrlEncoded
    fun verificationCaptcha(@FieldMap map: Map<String, String>): Observable<Response<Any>>

    /**
     * 绑定手机号
     */
    @PUT(API.BIND_NEW_MOBILE)
    @Headers("Authorization:true")
    @FormUrlEncoded
    fun bindNewMobile(@FieldMap map: Map<String, String>): Observable<Response<Any>>
}