package com.andrew.application.base

import com.andrew.library.base.AndrewApplication


class BaseApplication : AndrewApplication() {
    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    override fun jumpLoginActivity() {
//        val intent = Intent()
//        intent.setClass(instance, LoginNavActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent)
    }
}