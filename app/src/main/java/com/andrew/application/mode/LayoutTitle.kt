package com.andrew.application.mode

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.andrew.library.listener.AndrewOnClickListener

class LayoutTitle {
    var backListener: AndrewOnClickListener<View>? = null
    var title: String = ""
    var tvRightText: String = ""
    val tvRightListener = MutableLiveData<AndrewOnClickListener<View>>()
    val imgRightListener = MutableLiveData<AndrewOnClickListener<View>>()
    val hideBottomLine = MutableLiveData(true)

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