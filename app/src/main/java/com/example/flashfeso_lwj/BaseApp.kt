package com.example.lwj_base.common.base

import android.app.Application
import android.content.Context

open class BaseApp2: Application() {
    companion object{
         lateinit var context: Context

        private var instance: BaseApp2? = null


    }



    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

}