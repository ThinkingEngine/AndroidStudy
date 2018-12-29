package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.childmodela.UserInfo
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
    @POST(API.UPDATE_USER_INFO)
    @Headers("Authorization:true")
    @FormUrlEncoded
    fun updateUserInfo(@FieldMap map: Map<String, String>): Observable<Response<Any>>
}