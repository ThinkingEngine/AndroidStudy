package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo
import com.chengsheng.cala.htcm.protocol.FamiliesList
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
    fun getFamilies():Observable<Response<FamiliesList>>

    /*解除家人绑定*/
    @DELETE(API.DEL_FAM+"{families_id}")
    @Headers("Authorization:true")
    fun delFam(@Path("families_id")familiesDd:String):Observable<Response<ResponseBody>>

    /*获取家人详细信息*/
    @GET(API.GET_FAM_INFO+"{families_id}")
    @Headers("Authorization:true")
    fun getFamInfo(@Path("families_id")familiesDd: String):Observable<Response<FamiliesDetailInfo>>

    /*设置默认就诊人*/
    @POST(API.SET_DEFULT)
    @Headers("Authorization:true")
    fun setDefault(@Path("families_id")familiesDd: String):Observable<Response<ResponseBody>>

}