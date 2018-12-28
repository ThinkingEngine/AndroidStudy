package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.AssistantList
import io.reactivex.Observable


/**
 * Author: 蔡浪
 * CreateDate: 2018-12-28 10:31
 * Description:智能助理 接口服务
 */
class AIAssistantRepository {
    companion object {
        @Volatile
        private var instance: AIAssistantRepository? = null

        val default: AIAssistantRepository?
            get() {
                if (instance == null) {
                    synchronized(AIAssistantRepository::class.java) {
                        if (instance == null) {
                            instance = AIAssistantRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 智能助理
     *
     * excludeClosed:String ?
     * page:String 数据页数
     *
     * 返回类型:AssistantList.class
     * */
    fun getAIAssistants(excludeClosed:String,page:String): Observable<AssistantList>?{
        return transformProto(RetrofitHelper.getInstance().AIassistant.getAIAssistants(excludeClosed,page))
    }
}