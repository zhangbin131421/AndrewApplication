package com.andrew.library.net

import android.text.TextUtils
import com.andrew.library.net.interceptor.Level
import com.andrew.library.net.interceptor.LoggingInterceptor
import com.andrew.library.utils.ConstantsUtil
import com.blankj.utilcode.BuildConfig
import com.blankj.utilcode.util.SPStaticUtils
import okhttp3.*
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

object RetrofitHelper {
    var DEBUG = false

    fun getBuilder(apiUrl: String): Retrofit.Builder {
        val builder = Retrofit.Builder()
        builder.client(getOkClient())
        builder.baseUrl(apiUrl)
        builder.addConverterFactory(MyGsonConverterFactory.create())
        builder.addCallAdapterFactory(LiveDataCallAdapterFactory())
        return builder
    }

    fun getRetrofit(apiUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(getOkClient())
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitLiveData(apiUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(getOkClient())
            .baseUrl(apiUrl)
            .addConverterFactory(MyGsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    private fun getOkClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (DEBUG) {
            builder
//                .addNetworkInterceptor(LoggingInterceptor().apply {
//                    isDebug = true
//                    level = Level.BASIC
//                    type = Platform.INFO
//                    requestTag = "Request"
//                    responseTag = "Response"
//                })
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .sslSocketFactory(
                    SSLSocketClient.getSSLSocketFactory(),
                    object : X509TrustManager {
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate?> {
                            return arrayOfNulls(0)
                        }
                    }) //配置
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier()) //配置
        }
        return builder.retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .followRedirects(false)
            .cache(Cache(File("sdcard/cache", "okhttp"), 1024))
            .addInterceptor(RequestInterceptor())
//            .addInterceptor( RespInterceptor ())
            .build()
    }

    private class RequestInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest: Request = chain.request() //获取原始请求
            val requestBuilder: Request.Builder = originalRequest.newBuilder() //建立新的请求
//                .header("Connection", "close")
                .addHeader("Connection", "close")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .addHeader("filePlatform", "task")
                .removeHeader("User-Agent")
//                .addHeader("User-Agent", BaseUtils.getUserAgent())
                .method(originalRequest.method, originalRequest.body)
            val token = SPStaticUtils.getString(ConstantsUtil.TOKEN)
            if (TextUtils.isEmpty(token)) {
                return chain.proceed(requestBuilder.build())
            }
            val tokenRequest: Request = requestBuilder.header("token", token)
//                .header("companyId", SpUtil.getInstance().getCurrentCompanyId())
                .build()
            return chain.proceed(tokenRequest)
        }
    }

//    private class RespInterceptor : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val request: Request = chain.request()
//            val response: Response = chain.proceed(request)
//            if (response.code == 500) {
////                isTokenExpired(response);
//            }
//            //            Logger.d("response.code()= " + response.code());
////            Logger.d("response.body()= " + response.body().string());
//            return response
//        }
//    }
}