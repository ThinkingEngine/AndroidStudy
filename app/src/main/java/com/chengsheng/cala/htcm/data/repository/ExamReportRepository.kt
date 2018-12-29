package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.ExamReportList
import com.chengsheng.cala.htcm.protocol.ExamReportModel.CompareModel
import com.chengsheng.cala.htcm.protocol.childmodela.ExamReportDetial
import io.reactivex.Observable

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-29 9:58
 * Description:体检报告 服务
 */
class ExamReportRepository {

    companion object {
        @Volatile
        private var instance: ExamReportRepository? = null

        val default: ExamReportRepository?
            get() {
                if (instance == null) {
                    synchronized(ExamReportRepository::class.java) {
                        if (instance == null) {
                            instance = ExamReportRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 体检报告列表
     *
     * 获取给定的用户的体检报告列表
     * */
    fun getIssuedReport(customerID: String): Observable<ExamReportList>? {
        return transformProto(RetrofitHelper.getInstance().examReportService.getIssuedReport(customerID))
    }

    /**
     * 报告详情
     *
     * 获取用户的体检报告的详细情况
     * */
    fun getExamResult(orderId: String): Observable<ExamReportDetial>? {
        return transformProto(RetrofitHelper.getInstance().examReportService.getExamResult(orderId))
    }

    /**
     *报告对比
     *
     * 对用户的报告进行对比
     * */
    fun comparison(orderIdA: String, orderIdB: String): Observable<CompareModel>? {
        return transformProto(RetrofitHelper.getInstance().examReportService.comparison(orderIdA, orderIdB))
    }
}