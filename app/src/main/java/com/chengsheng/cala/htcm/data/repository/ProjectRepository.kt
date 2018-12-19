package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.RecommendProProtocol
import io.reactivex.Observable

/**
 * Author: 任和
 * CreateDate: 2018/12/19 10:18 AM
 * Description: 服务模块相关接口
 */
class ProjectRepository private constructor() {

    companion object {
        @Volatile
        private var instance: ProjectRepository? = null

        val default: ProjectRepository?
            get() {
                if (instance == null) {
                    synchronized(ProjectRepository::class.java) {
                        if (instance == null) {
                            instance = ProjectRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 获取推荐项目
     */
    fun getRecommendPro(): Observable<RecommendProProtocol>? {
        return transformProto(RetrofitHelper.getInstance().projectService.getRecommendPro())
    }

}