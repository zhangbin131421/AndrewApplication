package com.andrew.library.listener

import android.view.View
/**
 * @desc 防止多次点击抖动
 */
abstract class AndrewFastClickListener : View.OnClickListener {
    private var lastClickTime = 0L
    override fun onClick(v: View) {
        val now = System.currentTimeMillis()
        if (now - lastClickTime >= 400L) {
            lastClickTime = now
            onViewClick(v)
        }
    }

    abstract fun onViewClick(v: View?)
}