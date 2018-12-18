package com.chengsheng.cala.htcm.data

import android.accounts.NetworkErrorException
import android.content.Context
import com.chengsheng.cala.htcm.constant.GlobalConstant
import com.chengsheng.cala.htcm.data.retrofit.exception.BusinessException
import com.chengsheng.cala.htcm.data.retrofit.exception.InvalidLoginException
import com.chengsheng.cala.htcm.network.NetWorkUtils
import com.chengsheng.cala.htcm.protocol.BaseEntity
import com.chengsheng.cala.htcm.utils.PreferenceUtil
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Author: 任和
 * CreateDate: 2018/12/18 2:25 PM
 * Description:
 */
fun <T> transformProto(observable: Observable<BaseEntity<T>>?): Observable<T>? {
    return observable?.flatMap { protocol ->
        when {
            protocol.code.toString().startsWith("2") -> {
                Observable.just(protocol.data)
            }
            protocol.code.toString().startsWith("4") -> {
                Observable.error<T>(InvalidLoginException("登录已失效"))
            }
            protocol.code.toString().startsWith("5") -> {
                Observable.error<T>(BusinessException(protocol.code, "登录已失效"))
            }
            else -> {
                Observable.error<T>(BusinessException(protocol.code, protocol.message))
            }
        }
    }?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())
}

/**
 * body为Json格式的参数，直接将map转为Json格式
 */
fun createJson(map: Map<*, *>): RequestBody {
    return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSONObject(map).toString())
}

var NETWORK_FAILURE = "网络错误"

var REQUEST_FAILURE = ""

var RESPONSE_FAILURE = "数据请求失败"

var LOGIN_ILLEGAL = "登录已失效"


fun checkError(throwable: Throwable, context: Context): String {

    var errorMessage: String

    if (NetWorkUtils.getNetworkState(context) == GlobalConstant.NETWORK_NONE) {
        errorMessage = NETWORK_FAILURE
    } else if (throwable is BusinessException) {

        errorMessage = throwable.message ?: RESPONSE_FAILURE

    } else if (throwable is UnknownHostException || throwable is ConnectException
            || throwable is HttpException || throwable is NetworkErrorException) {
        errorMessage = REQUEST_FAILURE
        if (throwable is HttpException && throwable.code().toString().startsWith("4")) {
            errorMessage = if (throwable.message().isNullOrEmpty()) LOGIN_ILLEGAL else throwable.message()
            PreferenceUtil.clear()
        }
    } else if (throwable is JsonSyntaxException) {
        errorMessage = RESPONSE_FAILURE
    } else if (throwable is InvalidLoginException) {
        errorMessage = LOGIN_ILLEGAL
        PreferenceUtil.clear()

    } else {
        errorMessage = REQUEST_FAILURE
    }

    return errorMessage
}

