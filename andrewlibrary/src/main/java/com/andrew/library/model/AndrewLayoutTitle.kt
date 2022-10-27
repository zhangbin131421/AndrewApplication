package com.andrew.library.model

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.andrew.library.listener.AndrewOnClickListener

open class AndrewLayoutTitle {
    var backListener: AndrewOnClickListener<View>? = null
    var title: String = ""
    var tvRightText: String = ""
    var tvRightListener = MutableLiveData<AndrewOnClickListener<View>>()
    var imgRightListener = MutableLiveData<AndrewOnClickListener<View>>()

    fun clickBack(view: View) {
        backListener?.onClick(view)
    }

    fun clickTvRight(view: View) {
        tvRightListener.value?.onClick(view)
    }


    fun clickImgRight(view: View) {
        imgRightListener.value?.onClick(view)
    }


}