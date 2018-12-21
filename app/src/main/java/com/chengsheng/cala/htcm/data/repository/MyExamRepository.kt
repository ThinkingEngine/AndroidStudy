package com.chengsheng.cala.htcm.data.repository

import io.reactivex.Observable
import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.ExamItemsList

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-20 15:28
 * Description:
 */
class MyExamRepository {

    companion object {
        @Volatile
        private var instance: MyExamRepository? = null

        val default: MyExamRepository?
            get() {
                if (instance == null) {
                    synchronized(MyExamRepository::class.java) {
                        if (instance == null) {
                            instance = MyExamRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 我的体检列表
     */
    fun getExamList(mode: String, customerIds: String, page: String): Observable<ExamItemsList>? {
        return transformProto(RetrofitHelper.getInstance().myExamService.getExamList(mode, customerIds, page))
    }
}