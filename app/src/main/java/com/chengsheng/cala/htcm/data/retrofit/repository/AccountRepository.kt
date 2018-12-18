package com.chengsheng.cala.htcm.data.retrofit.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import io.reactivex.Observable

/**
 * Author: 任和
 * CreateDate: 2018/12/18 2:44 PM
 * Description: 账号相关的接口
 */
class AccountRepository private constructor() {

    companion object {
        @Volatile
        private var instance: AccountRepository? = null

        val default: AccountRepository?
            get() {
                if (instance == null) {
                    synchronized(AccountRepository::class.java) {
                        if (instance == null) {
                            instance = AccountRepository()
                        }
                    }
                }
                return instance
            }
    }

    fun login(): Observable<Any>? {
        return transformProto(RetrofitHelper.getInstance().userApi.allActivity())
    }
}