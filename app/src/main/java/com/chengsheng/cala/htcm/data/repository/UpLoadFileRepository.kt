package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.URLResult
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*


/**
 * Author: 蔡浪
 * CreateDate: 2018-12-26 18:57
 * Description:文件上传服务
 */
class UpLoadFileRepository {

    companion object {
        @Volatile
        private var instance: UpLoadFileRepository? = null

        val default: UpLoadFileRepository?
            get() {
                if (instance == null) {
                    synchronized(UpLoadFileRepository::class.java) {
                        if (instance == null) {
                            instance = UpLoadFileRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 上传图片
     */
    fun upLoad(file: File): Observable<URLResult>? {
        val descriptionString = RequestBody.create(MediaType.parse("multipart/form-data"), "avatar")
        val requestBody = RequestBody.create(MediaType.parse("image/jpg"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val map = HashMap<String, RequestBody>()
        map["bucket_name"] = descriptionString
        return transformProto(RetrofitHelper.getInstance().fileUpService.upLoadFile(map, body))
    }
}