package com.example.lwj_common.common.ui.controll.tools.utils

import android.Manifest
import android.content.Context
import android.location.LocationManager
import com.example.lwj_common.common.ui.controll.tools.utils.LocationUtils.LocationCallBack
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Location
import com.example.lwj_common.common.ui.controll.tools.utils.LocationUtils.GetLastLocation
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*
import javax.inject.Inject

class LocationUtils @Inject constructor() {
    var timer1: Timer? = null
    var lm: LocationManager? = null
    var locationCallBack: LocationCallBack? = null
    var context: Context? = null
    var gps_enabled = false
    var network_enabled = false


    /**
     * @return 返回1 : 网络或gps未开启(也可能手机没有google服务) 返回2 : 精准定位或大概定位权限未开启 返回0 :可以成功拿到location信息
     * @return 注意: 调用完该方法后, 若为0(也可以不判断直接设置), 则设置该对象的回掉实现
     * locationUtils.locationCallBack =  object : LocationUtils.LocationCallBack {...}这个要在前面
     * 如: val locationResultType: Int = locationUtils.isGetLocation(this)
     *
     * */
    fun isGetLocation(context: Context): Int {

        //locationCallBack = callBack
        this.context = context
        if (lm == null) lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            gps_enabled = lm !!.isProviderEnabled(LocationManager.GPS_PROVIDER) //是否开启GPS
            if (! gps_enabled) {
                throw RuntimeException("请提供gps服务")
                Log.e("location", "请开启GPS导航...") //开启导航服务才可以获取到位置
                // 返回开启GPS导航设置界面
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            context.startActivityForResult(intent, 0);
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            network_enabled = lm !!.isProviderEnabled(LocationManager.NETWORK_PROVIDER) //没有google服务的手机,返回false
            if (! network_enabled) {
                throw RuntimeException("请提供网络服务")
                Log.e("location", "无Google服务") //开启导航服务才会返回true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (! gps_enabled && ! network_enabled) return GetLocationResult.NO_GPS_OR_INTERNET_PROVIDER.ordinal
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("location", "权限缺失") //由于获取各种信息需要各种权限
            return GetLocationResult.NO_PERMISSION.ordinal
        }
        if (gps_enabled)
            lm !!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListenerGps)
        if (network_enabled)
            lm !!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListenerNetwork)
        timer1 = Timer()
        timer1 !!.schedule(GetLastLocation(), 20000)

        if (locationCallBack == null) {
            //throw RuntimeException("请设置LocationUtils对象的locationCallBack回掉实现")
            Log.e("location", "请设置LocationUtils对象的locationCallBack回掉实现")
            return GetLocationResult.SUCCESS.ordinal
        } else {
            return GetLocationResult.SUCCESS.ordinal
        }
    }

    enum class GetLocationResult {
        SUCCESS, NO_GPS_OR_INTERNET_PROVIDER, NO_PERMISSION
    }

    var locationListenerGps: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

//            Log.i("66666", "location: GPS");
            timer1 !!.cancel()
            locationCallBack !!.gotLocation(location)
            lm !!.removeUpdates(this)
            lm !!.removeUpdates(locationListenerNetwork)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }
    var locationListenerNetwork: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
//            Log.i("66666", "location: Network");
            timer1 !!.cancel()
            locationCallBack !!.gotLocation(location)
            lm !!.removeUpdates(this)
            lm !!.removeUpdates(locationListenerGps)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }

    internal inner class GetLastLocation : TimerTask() {
        override fun run() {
            lm !!.removeUpdates(locationListenerGps)
            lm !!.removeUpdates(locationListenerNetwork)
            var net_loc: Location? = null
            var gps_loc: Location? = null
            val other_loc: Location? = null
            if (ActivityCompat.checkSelfPermission(context !!,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context !!,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            if (gps_enabled) gps_loc = lm !!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (network_enabled) net_loc =
                    lm !!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.time > net_loc.time) locationCallBack !!.gotLocation(gps_loc) else locationCallBack !!.gotLocation(
                        net_loc)
                return
            }
            if (gps_loc != null) {
                locationCallBack !!.gotLocation(gps_loc)
                return
            }
            if (net_loc != null) {
                locationCallBack !!.gotLocation(net_loc)
                return
            }
            locationCallBack !!.gotLocation(null)
        }
    }

    //获取到Location位置后回调
    interface LocationCallBack {
        fun gotLocation(location: Location?)
    }
}