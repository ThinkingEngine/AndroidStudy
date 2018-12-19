package com.chengsheng.cala.htcm.data.service

import com.chengsheng.cala.htcm.constant.API
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * Author: 任和
 * CreateDate: 2018/12/19 5:18 PM
 * Description: 服务模块相关接口
 */
interface ProjectService {

    @GET(API.RECOMMEND_SERVICE)
    fun getRecommendPro(): Observable<Response<RecommendProProtocol>>

}