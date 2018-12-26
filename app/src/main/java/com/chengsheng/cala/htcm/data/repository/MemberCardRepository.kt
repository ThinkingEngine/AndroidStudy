package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.createJson
import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.MemberCardDetailProtocol
import com.chengsheng.cala.htcm.protocol.MemberCardProtocol
import com.google.gson.JsonObject
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

    /**
     * 会员卡详情
     */
    fun getCardDetail(id: Int): Observable<MemberCardDetailProtocol>? {
        return transformProto(RetrofitHelper.getInstance().memberCardService.getCardDetail(id))
    }

    /**
     * 解绑会员卡
     */
    fun deleteMemberCard(id: Int, password: String): Observable<Any>? {
        val map = HashMap<String, String>()
        map["password"] = password
        return transformProto(RetrofitHelper.getInstance().memberCardService.deleteMemberCard(id, createJson(map)))
    }

    /**
     * 发送验证码
     */
    fun sendCaptcha(mobile: String): Observable<JsonObject>? {
        return transformProto(RetrofitHelper.getInstance().memberCardService.sendCaptcha(mobile))
    }

    /**
     * 绑定会员卡
     */
    fun bind(cardNumber: String, password: String, mobile: String, captcha: String, uuid: String): Observable<JsonObject>? {
        return transformProto(RetrofitHelper.getInstance().memberCardService.bind(
                mobile, cardNumber, password, uuid, captcha))
    }

    /**
     * 修改卡密码
     */
    fun changePassword(id: Int, oldPassword: String, newPassword: String): Observable<Any>? {
        return transformProto(RetrofitHelper.getInstance().memberCardService.changePassword(
                id, oldPassword, newPassword))
    }

    /**
     * 找回卡密码-发送短信验证码
     */
    fun sendCardCaptcha(id: Int): Observable<JsonObject>? {
        return transformProto(RetrofitHelper.getInstance().memberCardService.sendCardCaptcha(id))
    }

    /**
     * 找回卡密码-验证短信验证码
     */
    fun checkCardCaptcha(id: Int, captcha: String, uuid: String): Observable<Any>? {
        return transformProto(RetrofitHelper.getInstance().memberCardService.checkCardCaptcha(
                id, captcha, uuid))
    }

    /**
     * 找回卡密码-发送短信验证码
     */
    fun findPassword(id: Int, password: String): Observable<Any>? {
        return transformProto(RetrofitHelper.getInstance().memberCardService.findPassword(
                id, password))
    }


}