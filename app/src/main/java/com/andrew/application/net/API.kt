package com.andrew.application.net

import androidx.lifecycle.LiveData
import com.andrew.application.BuildConfig
import com.andrew.application.mode.response.ContentModel
import com.andrew.library.model.BaseResponse
import com.andrew.library.net.RetrofitHelper
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

const val URL_ROOT_LOCAL = "http://121.41.29.237:9092" //
const val URL_ROOT_TEST = "https://lwtest.yibaobt.com" //测试环境
const val URL_ROOT_PRD = "https://lw.eyongtech.com" //正式环境
const val URL_ROOT = URL_ROOT_LOCAL
const val API_URL = "$URL_ROOT/"

val api: API by lazy {
    RetrofitHelper.DEBUG = BuildConfig.DEBUG
    RetrofitHelper.getRetrofit(API_URL).create(API::class.java)
}

val apiLiveData: API by lazy {
    RetrofitHelper.DEBUG = BuildConfig.DEBUG
    RetrofitHelper.getRetrofitLiveData(API_URL).create(API::class.java)
}

interface API {
    @POST("tcontent/search")
    fun contentSearch(@Body requestBody: RequestBody?): Call<BaseResponse<List<ContentModel>>>?

    @POST("tcontent/search")
    suspend fun contentSearchCoroutine(@Body requestBody: RequestBody?): BaseResponse<List<ContentModel>>?

    @POST("tcontent/search")
    fun contentSearchLiveData(@Body requestBody: RequestBody?): LiveData<BaseResponse<List<ContentModel>>?>?
}