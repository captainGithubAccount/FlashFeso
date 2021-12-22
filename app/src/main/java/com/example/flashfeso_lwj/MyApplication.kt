/*
package com.example.flashfeso_lwj

import android.app.Application
import android.util.Log
import com.adjust.sdk.Adjust
import com.example.flashfeso_lwj.flashfeso.utils.Constants

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        //crash收集
        if (Constants.debug) {
            val crashHandler: CrashHandler = CrashHandler.getInstance()
            crashHandler.init(applicationContext)
        }

        //摩比Liveness人脸识别
        val mobiSecret =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDsLg6wX4WPqeEGKnNhald5LtNRMOZy5jIEIeHqK5w8020oTT9JrHmF5/xdNZKoBXniqCyiBjtaxn9Y+mndUdxW40umyuL3AgqYaDP8jZCt9w1vLeVjDq54J/iqNrirpyyRJBtPdz6TT2JrSi1weuIOiYiDE5ardrGcbi+kU2ahIQIDAQAB"
        LivenessSDK.getInstance().init(this, "mexicoloanflash@163.com", mobiSecret)

        //Fresco
        val config: ImagePipelineConfig =
            ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build()
        Fresco.initialize(applicationContext, config)


        //GuardianLiveness人脸识别
        val accessKey = "386ff33ab8661a7b"
        val secretKey = "249fff678b6419e5"
        // 初始化方法不耗时，非必须在 application 中初始化，但要确保进入 LivenessActivity 之前完成调用
        GuardianLivenessDetectionSDK.init(this, accessKey, secretKey, Market.Mexico)

        //Adjust
        val appToken = "iy2ea6kf318g"
        var environment: String = AdjustConfig.ENVIRONMENT_SANDBOX
        //上线改掉模式
        environment = if (Constants.debug) {
            AdjustConfig.ENVIRONMENT_SANDBOX
        } else {
            AdjustConfig.ENVIRONMENT_PRODUCTION
        }
        val config2 = AdjustConfig(this, appToken, environment)
        config2.setUrlStrategy(AdjustConfig.URL_STRATEGY_CHINA)
        config2.setLogLevel(LogLevel.VERBOSE) // enable all logs
        config2.setOnAttributionChangedListener(object : OnAttributionChangedListener() {
            fun onAttributionChanged(attribution: AdjustAttribution) {
                Log.i("example", "Attribution callback called!")
                Log.i("example", "Attribution: " + attribution.toString())
                val trackerName: String = attribution.trackerName
                Log.i("example", "trackerName:$trackerName")
                InfoUtil.setChannel(trackerName)
            }
        })
        Adjust.onCreate(config2)
        registerActivityLifecycleCallbacks(AdjustLifecycleCallbacks())

        //Adjust调试
        config2.setOnEventTrackingFailedListener(object : OnEventTrackingFailedListener() {
            fun onFinishedEventTrackingFailed(eventFailureResponseData: AdjustEventFailure) {
                Log.e("Adjust", "Event failure callback called!")
                Log.e("Adjust", "Event failure data: $eventFailureResponseData")
            }
        })
        config2.setOnEventTrackingSucceededListener(object : OnEventTrackingSucceededListener() {
            fun onFinishedEventTrackingSucceeded(eventSuccessResponseData: AdjustEventSuccess) {
                Log.e("Adjust", "Event success callback called!")
                Log.e("Adjust", "Event success data: $eventSuccessResponseData")
            }
        })
        Adjust.getGoogleAdId(this, object : OnDeviceIdsRead() {
            fun onGoogleAdIdRead(googleAdId: String?) {
                InfoUtil.setGpsAdid(googleAdId)
            }
        })
    }

    private class AdjustLifecycleCallbacks : ActivityLifecycleCallbacks {
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

    companion object {
        val mEventBus: EventBus = EventBus.getDefault()

*
         * 获取Application实例
         *
         * @return Application的实例


        var instance: MyApplication? = null
            private set
public static int getLoac() {

        LocationUtils locationUtils = new LocationUtils();

//        Log.i("66666", "location: true");
        int location = locationUtils.getLocation(instance, new LocationUtils.LocationCallBack() {
            @Override
            public void gotLocation(Location location) {

//                Log.i("66666", "location: in");
                if (location != null) {
                    //当前经度
                    double longitude = location.getLongitude();
                    //当前纬度
                    double latitude = location.getLatitude();

                    if (longitude != 0) {
                        InfoUtil.setLongitude(longitude + "");
                    }

                    if (latitude != 0) {
                        InfoUtil.setLatitude(latitude + "");
                    }
//                    Log.i("66666", "longitude: "+longitude+" latitude: "+latitude);
                }
            }
        });

//        Log.i("66666", "location: true"+location);
        return location;
    }

    }
}
*/
