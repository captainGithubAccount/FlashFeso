package com.example.flashfeso_lwj

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    companion object{
        val ISDEBUG = true
        var instance: App? = null

        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

}