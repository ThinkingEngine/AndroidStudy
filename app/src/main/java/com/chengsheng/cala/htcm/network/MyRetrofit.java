package com.chengsheng.cala.htcm.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {

    private static final int DEFAULT_TIME_OUT = 5;
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private static final String PROTOCOL = "http";
    private static final String BASE_URL = "account.zz-tech.com.cn";
    private static final String PORT = "85";

    private Retrofit retrofit;

    private static volatile MyRetrofit myRetrofit;

    private MyRetrofit() {
        if (myRetrofit != null) {
            throw new RuntimeException("Retrofit 实例化异常!");
        }


    }

    public static MyRetrofit createInstance() {

        if (myRetrofit == null) {
            synchronized (MyRetrofit.class) {
                if (myRetrofit == null) {
                    myRetrofit = new MyRetrofit();
                }
            }

        }
        return myRetrofit;
    }

    public Retrofit createDefault(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().client(builder.build()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                baseUrl(PROTOCOL + "://" + BASE_URL + ":" + PORT+"/").
                build();

        return  retrofit;
    }

    public Retrofit createURL(String url){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();

        return retrofit;
    }


    public <T> T create(Retrofit retrofit,Class<T> service) {
        return retrofit.create(service);
    }

    public void destory(Retrofit retrofit){
        retrofit = null;
    }

}
