package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo
import com.chengsheng.cala.htcm.protocol.FamiliesList
import com.chengsheng.cala.htcm.protocol.Message
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * Author: 蔡浪
 * CreateDate: 2018-12-25 9:44
 * Description:家庭成员
 */
class MemberRepository {

    companion object {
        @Volatile
        private var instance: MemberRepository? = null

        val default: MemberRepository?
            get() {
                if (instance == null) {
                    synchronized(MemberRepository::class.java) {
                        if (instance == null) {
                            instance = MemberRepository()
                        }
                    }
                }
                return instance
            }
    }

    fun getMember(): Observable<FamiliesList>? {
        return transformProto(RetrofitHelper.getInstance().memberService.getFamilies())
    }

    fun delFam(familyId: String): Observable<ResponseBody>? {
        return transformProto(RetrofitHelper.getInstance().memberService.delFam(familyId))
    }

    fun getFamInfo(familyId: String): Observable<FamiliesDetailInfo>? {
        return transformProto(RetrofitHelper.getInstance().memberService.getFamInfo(familyId))
    }

    fun setDefault(familyId: String): Observable<ResponseBody>? {
        return transformProto(RetrofitHelper.getInstance().memberService.setDefault(familyId))
    }

    fun sendModCode(familyId: String): Observable<Message>? {
        return transformProto(RetrofitHelper.getInstance().memberService.sendModCode(familyId))
    }

    fun valiModeCoe(familyId: String, uuid: String, code: String): Observable<ResponseBody>? {
        return transformProto(RetrofitHelper.getInstance().memberService.valiModeCode(familyId, uuid, code))
    }

    fun putFamInfo(familyId: String, map: Map<String, String>): Observable<ResponseBody>? {
        return transformProto(RetrofitHelper.getInstance().memberService.putFamInfo(familyId, map))
    }

    fun vailCode(familyId: String, phone: String): Observable<Message>? {
        return transformProto(RetrofitHelper.getInstance().memberService.smsCode(familyId, phone))
    }

    fun vailCell(familyId: String, map: Map<String, String>): Observable<ResponseBody>? {
        return transformProto(RetrofitHelper.getInstance().memberService.vailCell(familyId, map))
    }

    fun addMem(map: Map<String, String>): Observable<ResponseBody>? {
        return transformProto(RetrofitHelper.getInstance().memberService.addMem(map))
    }
}