package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.MemberCardProtocol
import io.reactivex.Observable

/**
 * Author: 任和
 * CreateDate: 2018/12/15 8:50 AM
 * Description: 会员卡模块相关接口
 */
class MemberCardRepository private constructor() {

    companion object {
        @Volatile
        private var instance: MemberCardRepository? = null

        val default: MemberCardRepository?
            get() {
                if (instance == null) {
                    synchronized(MemberCardRepository::class.java) {
                        if (instance == null) {
                            instance = MemberCardRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 获取会员卡列表
     */
    fun getCardList(): Observable<MemberCardProtocol>? {
        return transformProto(RetrofitHelper.getInstance().memberCardService.getCardList())
    }

}