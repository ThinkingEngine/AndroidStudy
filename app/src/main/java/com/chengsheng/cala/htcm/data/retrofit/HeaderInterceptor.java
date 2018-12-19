package com.chengsheng.cala.htcm.data.retrofit;

import com.chengsheng.cala.htcm.utils.UserUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * API接口请求头拦截器
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        String accessToken = request.header("Authorization");

        if (Boolean.valueOf(accessToken)) {
            newBuilder.removeHeader("Authorization");
            newBuilder.addHeader(UserUtil.getTokenType(), UserUtil.getAccessToken());
        }

        Request newRequest = newBuilder.build();
        return chain.proceed(newRequest);
    }
}
