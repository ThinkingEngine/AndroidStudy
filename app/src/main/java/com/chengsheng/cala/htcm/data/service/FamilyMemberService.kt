package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo
import com.chengsheng.cala.htcm.protocol.FamiliesList
import com.chengsheng.cala.htcm.protocol.Message
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-25 9:15
 * Description:家庭成员服务
 */
interface FamilyMemberService {

    @GET(API.FAMILY_MEMBER)
    @Headers("Authorization:true")
    fun getFamilies(): Observable<Response<FamiliesList>>

    /* 解除家人绑定 */
    @DELETE(API.DEL_FAM + "{families_id}")
    @Headers("Authorization:true")
    fun delFam(@Path("families_id") familiesDd: String): Observable<Response<ResponseBody>>

    /* 获取家人详细信息 */
    @GET(API.GET_FAM_INFO + "{families_id}")
    @Headers("Authorization:true")
    fun getFamInfo(@Path("families_id") familiesDd: String): Observable<Response<FamiliesDetailInfo>>

    /* 设置默认就诊人 */
    @POST(API.SET_DEFULT)
    @Headers("Authorization:true")
    fun setDefault(@Path("families_id") familiesDd: String): Observable<Response<ResponseBody>>

    /* 发送短信验证码-修改家人信息 */
    @POST(API.SEND_MOD_CODE)
    @Headers("Authorization:true")
    fun sendModCode(@Path("families_id") familiesDd: String): Observable<Response<Message>>

    /* 验证短信验证码-修改家人信息 */
    @POST(API.VALI_MOD_CODE)
    @FormUrlEncoded
    @Headers("Authorization:true")
    fun valiModeCode(@Path("families_id") familiesDd: String, @Field("uuid") uuid: String, @Field("code") code: String): Observable<Response<ResponseBody>>

    /* 修改家庭成员信息 */
    @PUT(API.PUT_FAM)
    @FormUrlEncoded
    @Headers("Authorization:true")
    fun putFamInfo(@Path("families_id")familiesDd: String,@FieldMap map:Map<String,String>):Observable<Response<ResponseBody>>

    /* 发送短信验证码-修改家人手机号 */
    @POST(API.VAIL_CODE)
    @FormUrlEncoded
    @Headers("Authorization:true")
    fun smsCode(@Path("families_id") familiesDd: String,@Field("mobile")phone:String):Observable<Response<Message>>

    /* 验证短信验证码-修家人改手机号 */
    @POST(API.VAIL_CELL)
    @FormUrlEncoded
    @Headers("Authorization:true")
    fun vailCell(@Path("families_id")familiesDd: String, @FieldMap map: Map<String, String>):Observable<Response<ResponseBody>>

    /* 添加家庭成员 */
    @POST(API.ADD_MEM)
    @FormUrlEncoded
    @Headers("Authorization:true")
    fun addMem(@FieldMap map: Map<String, String>):Observable<Response<ResponseBody>>
}