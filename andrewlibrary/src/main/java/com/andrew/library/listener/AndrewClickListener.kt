package com.andrew.library.listener

import android.view.View

abstract class AndrewClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0
    private var timeInterval = 1000L

    override fun onClick(v: View) {
        val nowTime = System.currentTimeMillis()
        if ((nowTime - lastClickTime) > timeInterval) {
            // 单次点击事件
            onSingleClick()
            lastClickTime = nowTime
        } else {
            // 快速点击事件
            onFastClick()
        }
    }

    protected abstract fun onSingleClick()
    protected abstract fun onFastClick()
}