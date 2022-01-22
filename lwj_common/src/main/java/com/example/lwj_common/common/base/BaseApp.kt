package com.example.lwj_base.common.base

import android.app.Application
import android.content.Context

/**
 * 注意该类必须被继承, 否则SharedPreferenceUtil取不到context
 * */
abstract class BaseApp: Application() {
    companion object{
        lateinit var appContext: Context

        var instance: BaseApp? = null


    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
    }
}

/*




*/