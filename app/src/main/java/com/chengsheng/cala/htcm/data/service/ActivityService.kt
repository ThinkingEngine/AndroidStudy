package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.protocol.BaseEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers


interface ActivityService {

    @GET("")
    @Headers("Authorization:true")
    fun allActivity(): Observable<BaseEntity<Any>>

}