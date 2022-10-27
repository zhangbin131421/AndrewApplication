package com.andrew.library.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex


open class AndrewApplication : Application() {
    companion object {
        lateinit var instance: AndrewApplication
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        MMKV.initialize(this)// /data/user/0/com.szybkj.task/files/mmkv

    }

    open fun jumpLoginActivity() {}
}