package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.childmodela.UserInfo
import io.reactivex.Observable

/**
 * Author: 任和
 * CreateDate: 2018/12/19 10:18 AM
 * Description: 用户模块相关接口
 */
class UserRepository private constructor() {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        val default: UserRepository?
            get() {
                if (instance == null) {
                    synchronized(UserRepository::class.java) {
                        if (instance == null) {
                            instance = UserRepository()
                        }
                    }
                }
                return instance
            }
    }

    /**
     * 获取用户信息
     */
    fun getUserInfo(): Observable<UserInfo>? {
        return transformProto(RetrofitHelper.getInstance().userApi.getUserInfo())
    }

}