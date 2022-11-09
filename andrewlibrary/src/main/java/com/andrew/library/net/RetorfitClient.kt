package com.andrew.library.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun retrofit(apiUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun getOkHttpClient(): OkHttpClient {
    val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS) //设置读取超时时间
        .writeTimeout(30, TimeUnit.SECONDS) //设置写的超时时间
        .connectTimeout(30, TimeUnit.SECONDS)
//    if (BuildConfig.DEBUG) {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    builder.addInterceptor(httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    })
//    }
    return builder.build()
}

