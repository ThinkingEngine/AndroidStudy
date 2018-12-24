package com.chengsheng.cala.htcm.data.retrofit

import com.chengsheng.cala.htcm.BuildConfig
import com.chengsheng.cala.htcm.data.retrofit.convert.CustomGsonConverterFactory
import com.chengsheng.cala.htcm.data.service.ExamOrderService
import com.chengsheng.cala.htcm.data.service.OrganizationService
import com.chengsheng.cala.htcm.data.service.ProjectService
import com.chengsheng.cala.htcm.data.service.UserService
import com.chengsheng.cala.htcm.utils.UserUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RetrofitHelper private constructor() {

    init {
        retrofit = Retrofit.Builder().baseUrl("http://www.baidu.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client).build()

        httpsRetrofit = Retrofit.Builder().baseUrl("http://www.baidu.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpsClient).build()
    }

    private fun <T> httpCreate(clazz: Class<T>): T = retrofit!!.create(clazz)

    private fun <T> httpsCreate(clazz: Class<T>): T = httpsRetrofit!!.create(clazz)
//    private fun <T> httpsCreate(clazz: Class<T>): T = retrofit!!.create(clazz)


    companion object {

        private var retrofitHelper: RetrofitHelper? = null

        private var retrofit: Retrofit? = null

        private var httpsRetrofit: Retrofit? = null

        fun getInstance(): RetrofitHelper {
            if (retrofitHelper == null) {
                synchronized(RetrofitHelper::class.java) {
                    if (retrofitHelper == null) {
                        retrofitHelper = RetrofitHelper()
                    }
                }
            }
            return retrofitHelper!!
        }

        private val client: OkHttpClient
            get() {
                val logging = HttpLoggingInterceptor()
                if (BuildConfig.DEBUG) {
                    logging.level = HttpLoggingInterceptor.Level.BODY
                }

                return OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor(HeaderInterceptor()).addInterceptor(logging).addInterceptor(object : Interceptor {
                            override fun intercept(chain: Interceptor.Chain?): Response? {
                                val response = chain?.proceed(chain.request())
                                val headler = response?.headers()

                                val access_token = headler?.get("access_token")
                                if (!access_token.isNullOrEmpty()) {
                                    UserUtil.setAccessToken(access_token)
                                }
                                return response
                            }
                        }).build()
            }

        private val httpsClient: OkHttpClient
            get() {
                val builder = OkHttpClient.Builder()
                try {
                    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                        override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

                        override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

                        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                            return arrayOf()
                        }
                    })

                    val sslContext = SSLContext.getInstance("SSL")

                    sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                    val sslSocketFactory = sslContext.socketFactory

                    builder.sslSocketFactory(sslSocketFactory)
                    builder.hostnameVerifier({ hostname, session -> true })
                } catch (e: Exception) {
                }
                val logging = HttpLoggingInterceptor()
                if (BuildConfig.DEBUG) {
                    logging.level = HttpLoggingInterceptor.Level.BODY
                }

                builder.readTimeout(20, TimeUnit.SECONDS)
                builder.connectTimeout(15, TimeUnit.SECONDS)
                builder.addInterceptor(HeaderInterceptor())
                builder.addInterceptor(logging)
                return builder.build()
            }
    }

    //用户账号个人信息
    var userApi = httpsCreate(UserService::class.java)
    //订单
    var orderService = httpCreate(ExamOrderService::class.java)
    //服务
    var projectService = httpCreate(ProjectService::class.java)

    var myExamService = httpCreate(com.chengsheng.cala.htcm.data.service.MyExamService::class.java)

    //机构
    var organizationService = httpCreate(OrganizationService::class.java)

    //套餐
    var comboService = httpCreate()

}
