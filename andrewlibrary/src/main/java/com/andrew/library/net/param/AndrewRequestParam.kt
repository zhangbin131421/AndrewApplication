package com.andrew.library.net.param

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

open class AndrewRequestParam {
    fun toRequestBody(): RequestBody {
        return Gson().toJson(this).toRequestBody("application/json;charset=utf-8".toMediaType())
    }
}