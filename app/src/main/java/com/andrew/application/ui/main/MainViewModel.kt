package com.andrew.application.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.andrew.application.base.viewmodel.BaseViewModel
import com.andrew.application.mode.request.RequestContentSearch
import com.andrew.application.mode.response.ContentModel
import com.andrew.application.net.api
import com.andrew.application.net.apiLiveData
import com.andrew.application.net.wanAndroidAPI
import com.andrew.library.model.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainViewModel : BaseViewModel() {
    val content = MutableLiveData<String>()
    val contentSearchLiveData = Transformations.switchMap(refreshTrigger) {
        val requestContentSearch =
            RequestContentSearch(
                pageIndex = 1,
                pageSize = 10,
                searchType = 0,
                content = "苏州",
                lon = 0.0,
                lat = 0.0
            )
        apiLiveData.contentSearchLiveData(requestContentSearch.toRequestBody())
    }

    fun contentSearchLiveData(): LiveData<BaseResponse<List<ContentModel>>?>? {
        val requestContentSearch =
            RequestContentSearch(
                pageIndex = 1,
                pageSize = 10,
                searchType = 0,
                content = "苏州",
                lon = 0.0,
                lat = 0.0
            )
        return apiLiveData.contentSearchLiveData(requestContentSearch.toRequestBody())
    }


    fun contentSearchCoroutine() {
        val requestContentSearch =
            RequestContentSearch(
                pageIndex = 1,
                pageSize = 10,
                searchType = 0,
                content = "苏州",
                lon = 0.0,
                lat = 0.0
            )
        viewModelScope.launch(Dispatchers.IO) {
            val result = api.contentSearchCoroutine(requestContentSearch.toRequestBody())
            result?.let {
                content.postValue(result.data?.size.toString())
            }
        }
    }

    fun contentSearch() {
        val requestContentSearch =
            RequestContentSearch(
                pageIndex = 1,
                pageSize = 10,
                searchType = 0,
                content = "苏州",
                lon = 0.0,
                lat = 0.0
            )
        api.contentSearch(requestContentSearch.toRequestBody())
            ?.enqueue(object : retrofit2.Callback<BaseResponse<List<ContentModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<ContentModel>>>,
                    response: Response<BaseResponse<List<ContentModel>>>
                ) {
                }

                override fun onFailure(call: Call<BaseResponse<List<ContentModel>>>, t: Throwable) {
                }

            })
    }


    fun getListProject() {
        viewModelScope.launch(Dispatchers.IO) {
            wanAndroidAPI.getListProject()
        }
    }


}