package com.andrew.application.net

import com.andrew.application.mode.response.wan.ListProjectBean
import com.andrew.library.model.BaseResponse
import com.andrew.library.net.retrofit
import retrofit2.http.GET


val wanAndroidAPI: WanAndroidAPI by lazy {
    retrofit("https://www.wanandroid.com").create(WanAndroidAPI::class.java)
}

interface WanAndroidAPI {
    @GET("/article/listproject/0/json")
    suspend fun getListProject(): BaseResponse<ListProjectBean?>
}