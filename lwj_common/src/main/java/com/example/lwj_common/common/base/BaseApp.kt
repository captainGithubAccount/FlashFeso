package com.example.lwj_base.common.base

import android.app.Application
import android.content.Context

abstract class BaseApp: Application() {
    companion object{
        lateinit var _context: Context

        var _instance: BaseApp? = null


    }

    override fun onCreate() {
        super.onCreate()
        _instance = this
        _context = applicationContext
    }

}