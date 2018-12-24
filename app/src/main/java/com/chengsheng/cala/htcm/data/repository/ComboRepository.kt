package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.ExamApponitments
import io.reactivex.Observable

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-24 15:05
 * Description:套餐模块相关接口
 */
class ComboRepository {

    companion object {
        @Volatile
        private var instance: ComboRepository? = null

        val default: ComboRepository?
            get() {
                if (instance == null) {
                    synchronized(ComboRepository::class.java) {
                        if (instance == null) {
                            instance = ComboRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 套餐列表启用的
     *  根据搜索条件获取套餐
     * */

    fun getComboInfoFilters(filters: String, page: String, sortFields: String): Observable<ExamApponitments>? {
        return transformProto(RetrofitHelper.getInstance().comboService.getComboInfoFilters(filters, page, sortFields))
    }
}