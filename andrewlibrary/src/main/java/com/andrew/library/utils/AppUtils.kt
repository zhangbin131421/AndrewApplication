package com.andrew.library.utils

import android.content.Intent
import com.andrew.library.base.AndrewApplication


object AppUtils {

    fun getAuthority(): String {
        return "${AndrewApplication.instance.packageName}.fileprovider"
    }

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private const val MIN_CLICK_DELAY_TIME = 1000
    private var lastClickTime: Long = 0
    fun isNormalClick(): Boolean {
        val curClickTime = System.currentTimeMillis()
        val flag = (curClickTime - lastClickTime) > MIN_CLICK_DELAY_TIME
        lastClickTime = curClickTime
        return flag
    }

    private var mActivityJumpTag = ""

    /**
     * 检查是否重复跳转，不需要则重写方法并返回true
     */
    fun checkIntentActivity(intent: Intent): Boolean {

        // 默认检查通过
        var result = true
        // 标记对象
        val tag = when {
            intent.component != null -> { // 显式跳转
                intent.component!!.className
            }
            intent.action != null -> { // 隐式跳转
                intent.action!!
            }
            else -> {
                return false
            }
        }
        if (!isNormalClick() && tag == mActivityJumpTag) { // 检查不通过
            result = false
        }
        // 记录启动标记和时间
        mActivityJumpTag = tag
        return result
    }

}