package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.childmodelb.ExamOrder
import com.chengsheng.cala.htcm.protocol.childmodelb.UserExamDetail
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Author: 任和
 * CreateDate: 2018/12/19 10:18 AM
 * Description: 体检订单模块相关接口
 */
class ExamOrderRepository private constructor() {

    companion object {
        @Volatile
        private var instance: ExamOrderRepository? = null

        val default: ExamOrderRepository?
            get() {
                if (instance == null) {
                    synchronized(ExamOrderRepository::class.java) {
                        if (instance == null) {
                            instance = ExamOrderRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 体检订单信息
     */
    fun getUserExamDetail(orderId: String): Observable<UserExamDetail>? {
        return transformProto(RetrofitHelper.getInstance().orderService.getUserExamDetail(orderId))
    }

    /**
     * 自助登记
     */
    fun registration(orderId: String): Observable<ResponseBody>? {
        return transformProto(RetrofitHelper.getInstance().orderService.registration(orderId))
    }

    /**
     * 订单列表
     * */
    fun getExamOrder(id: String, mode: String, page: String): Observable<ExamOrder>? {
        return transformProto(RetrofitHelper.getInstance().orderService.getExamOrder(id, mode, page))
    }

}