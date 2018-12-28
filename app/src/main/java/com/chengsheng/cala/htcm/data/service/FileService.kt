package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.URLResult
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-26 18:49
 * Description:文件上传服务
 */
interface FileService {

    @POST(API.HEADER_UP)
    @Multipart
    @Headers("Authorization:true")
    fun upLoadFile(@PartMap map: Map<String, @JvmSuppressWildcards RequestBody>, @Part file: MultipartBody.Part): Observable<Response<URLResult>>

}