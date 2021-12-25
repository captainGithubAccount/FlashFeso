package com.example.flashfeso_lwj

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.adjust.sdk.*
import com.example.flashfeso_lwj.base.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {


    @Inject
    lateinit var mInfoUtil: InfoUtil

    companion object{
        lateinit var context: Context

        var instance: App? = null


    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext

        //Adjust start ----------------------------------------------------------------------
        val appToken = "wn2muq29ak8w"
        var environment = AdjustConfig.ENVIRONMENT_SANDBOX
        //上线改掉模式
        environment = if (Constants.debug) {
            AdjustConfig.ENVIRONMENT_SANDBOX
        } else {
            AdjustConfig.ENVIRONMENT_PRODUCTION
        }
        val config2 = AdjustConfig(this, appToken, environment)
        config2.setUrlStrategy(AdjustConfig.URL_STRATEGY_CHINA)
        config2.setLogLevel(LogLevel.VERBOSE) // enable all logs

        config2.setOnAttributionChangedListener { attribution ->
            Log.i("example", "Attribution callback called!")
            Log.i("example", "Attribution: $attribution")
            val trackerName = attribution.trackerName
            Log.i("example", "trackerName:$trackerName")
            mInfoUtil.channel = trackerName
        }
        Adjust.onCreate(config2)

        registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())

        //Adjust调试
        config2.setOnEventTrackingFailedListener { eventFailureResponseData ->
            Log.e("Adjust", "Event failure callback called!")
            Log.e("Adjust", "Event failure data: $eventFailureResponseData")
        }

        config2.setOnEventTrackingSucceededListener { eventSuccessResponseData ->
            Log.e("Adjust", "Event success callback called!")
            Log.e("Adjust", "Event success data: $eventSuccessResponseData")
        }

        Adjust.getGoogleAdId(this
        ) { googleAdId -> mInfoUtil.gpsAdid = googleAdId }
        //Adjust end ----------------------------------------------------------------------





    }



    // adjust --------------------------------------------------------------
    class AdjustLifecycleCallbacks : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        override fun onActivityStarted(activity: Activity) {}
        override fun onActivityResumed(activity: Activity) {
            Adjust.onResume()
        }

        override fun onActivityPaused(activity: Activity) {
            Adjust.onPause()
        }

        override fun onActivityStopped(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }

}