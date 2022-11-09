package com.andrew.application.mode.request

import com.andrew.library.net.param.AndrewRequestParam

data class RequestContentSearch(
    val pageIndex: Int,
    val pageSize: Int,
    val searchType: Int,
    val content: String,
    val lon: Double,
    val lat: Double,
) : AndrewRequestParam()
