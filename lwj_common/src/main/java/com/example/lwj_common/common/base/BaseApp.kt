package com.example.lwj_base.common.base

import android.app.Application
import android.content.Context

abstract class BaseApp: Application() {
    companion object{
        lateinit var context: Context

        var instance: BaseApp? = null


    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

}