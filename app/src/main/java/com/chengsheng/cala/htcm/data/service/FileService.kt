package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.URLResult
import okhttp3.MultipartBody
import retrofit2.Response
import io.reactivex.Observable
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-26 18:49
 * Description:文件上传服务
 */
interface FileService {

    @POST(API.HEADER_UP)
    @Multipart
    fun upHeader(@PartMap map: Map<String,String>, @Part file: MultipartBody.Part):Observable<Response<URLResult>>
}