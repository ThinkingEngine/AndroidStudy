package com.chengsheng.cala.htcm.data

import android.content.Context
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.retrofit.exception.BusinessException
import com.chengsheng.cala.htcm.data.retrofit.exception.InvalidLoginException
import com.chengsheng.cala.htcm.module.account.LoginActivity
import com.chengsheng.cala.htcm.network.NetWorkUtils
import com.chengsheng.cala.htcm.utils.CleanUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Author: 任和
 * CreateDate: 2018/12/18 2:25 PM
 * Description: 接口请求返回的扩展类
 */
fun <T> transformProto(observable: Observable<Response<T>>?): Observable<T>? {
    return observable?.flatMap { protocol ->
        when {
            protocol.code().toString().startsWith("2") -> {
                Observable.just(protocol.body()!!)
            }
            protocol.code() == 401 -> {
                Observable.error<T>(InvalidLoginException("登录已失效"))
            }
            protocol.code().toString().startsWith("5") -> {
                Observable.error<T>(BusinessException(protocol.code(), "当前服务不可以"))
            }
            else -> {
                Observable.error<T>(BusinessException(protocol.code(), protocol.message()))
            }
        }
    }?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())
}

var NETWORK_FAILURE = "网络错误"

var REQUEST_FAILURE = ""

var RESPONSE_FAILURE = "数据请求失败"

var LOGIN_ILLEGAL = "登录已失效"


fun checkError(throwable: Throwable, context: Context): String {

    val errorMessage: String

    when {
        NetWorkUtils.getNetworkState(context) == GlobalConstant.NETWORK_NONE -> errorMessage = NETWORK_FAILURE
        throwable is BusinessException -> errorMessage = throwable.message ?: RESPONSE_FAILURE
        throwable is InvalidLoginException -> {
            errorMessage = LOGIN_ILLEGAL
            CleanUtil.cleanAllLoginInfo()
            LoginActivity.start(context)
        }
        else -> errorMessage = REQUEST_FAILURE
    }

    return errorMessage
}

