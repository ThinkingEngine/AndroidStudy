package com.chengsheng.cala.htcm.data.repository

import com.chengsheng.cala.htcm.data.retrofit.RetrofitHelper
import com.chengsheng.cala.htcm.data.transformProto
import com.chengsheng.cala.htcm.protocol.FamiliesDetailInfo
import com.chengsheng.cala.htcm.protocol.FamiliesList
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
}