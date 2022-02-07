package com.example.flashfeso_lwj

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.adjust.sdk.*
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.example.flashfeso_lwj.flashfeso.utils.SharedPreferenceUtils

import com.example.lwj_base.common.base.BaseApp2
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: BaseApp2() {
    /*companion object{
        fun  getApplicationContext(): Context = context
    }*/

    override fun onCreate() {
        super.onCreate()
        /*_instance = this
        _context = applicationContext*/
        //InfoUtil
        //SharedPreferenceUtils

        //Fresco

        //Fresco
        val config = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build()
        Fresco.initialize(applicationContext, config)


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
            InfoUtil.channel = trackerName
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
        ) { googleAdId -> InfoUtil.gpsAdid = googleAdId }
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