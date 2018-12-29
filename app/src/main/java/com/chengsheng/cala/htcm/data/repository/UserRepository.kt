package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.childmodela.UserInfo
import com.google.gson.JsonObject
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

    /**
     * 修改用户信息
     */
    fun updateUserInfo(name: String, nickname: String, avatar: String): Observable<Any>? {
        val map = HashMap<String, String>()
        if (name.isNotEmpty()) {
            map["name"] = name
        }
        if (nickname.isNotEmpty()) {
            map["nickname"] = nickname
        }
        if (avatar.isNotEmpty()) {
            map["avatar_url"] = avatar
        }
        return transformProto(RetrofitHelper.getInstance().userApi.updateUserInfo(map))
    }


    /**
     * 发送短信验证码
     * @param type registration,change_phone_number,verification_user_self,change_password,bind_phone_number
     */
    fun sendCaptcha(mobile: String, type: String): Observable<JsonObject>? {
        val map = HashMap<String, String>()
        map["phone_number"] = mobile
        map["type"] = type
        return transformProto(RetrofitHelper.getInstance().userApi.sendCaptcha(map))
    }

    /**
     * 验证短信验证码
     * @param type registration,change_phone_number,verification_user_self,change_password,bind_phone_number
     */
    fun verificationCaptcha(code: String, codeId: String, type: String): Observable<Any>? {
        val map = HashMap<String, String>()
        map["code"] = code
        map["code_id"] = codeId
        map["type"] = type
        return transformProto(RetrofitHelper.getInstance().userApi.verificationCaptcha(map))
    }

    /**
     * 绑定新手机号
     * @param type 验证类型，old_phone_number|password
     * @param password 密码 如果验证类型是password，此参数必传
     * @param newCodeId 新手机号的code_id
     * @param oldCodeId 如果验证类型是手机号次参数必填，旧手机号的code_id
     * @param newMobile 新手机号
     *
     */
    fun bindNewMobile(type: String, password: String, newCodeId: String,
                      oldCodeId: String, newMobile: String): Observable<Any>? {
        val map = HashMap<String, String>()
        map["vcerification_type"] = type
        map["password"] = password
        map["new_phone_number_verification_code_id"] = newCodeId
        map["old_phone_number_verification_code_id"] = oldCodeId
        map["new_phone_number"] = newMobile
        return transformProto(RetrofitHelper.getInstance().userApi.bindNewMobile(map))
    }

    /**
     * 重置登录密码
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @param codeId codeId
     * @param type old_password和phone_number
     */
    fun resetLoginPassword(newPassword: String, oldPassword: String, codeId: String,
                           type: String, mobile: String): Observable<Any>? {
        val map = HashMap<String, String>()
        map["new_password"] = newPassword
        map["old_password"] = oldPassword
        map["phone_number_verification_code_id"] = codeId
        map["vcerification_type"] = type
        map["username"] = mobile
        return transformProto(RetrofitHelper.getInstance().userApi.resetLoginPassword(map))
    }
}