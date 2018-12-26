package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.URLResult
import io.reactivex.Observable
import okhttp3.MultipartBody


/**
 * Author: 蔡浪
 * CreateDate: 2018-12-26 18:57
 * Description:文件上传服务
 */
class FileUpRepository {

    companion object {
        @Volatile
        private var instance: FileUpRepository? = null

        val default: FileUpRepository?
            get() {
                if (instance == null) {
                    synchronized(FileUpRepository::class.java) {
                        if (instance == null) {
                            instance = FileUpRepository()
                        }
                    }
                }
                return instance
            }
    }

    fun headerUp(map: Map<String, String>, file: MultipartBody.Part): Observable<URLResult>? {
        return transformProto(RetrofitHelper.getInstance().fileUpService.upHeader(map, file))
    }
}