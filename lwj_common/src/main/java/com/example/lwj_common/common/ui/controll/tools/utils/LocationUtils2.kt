package com.example.lwj_common.common.ui.controll.tools.utils

import android.Manifest
import android.content.Context
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import java.lang.Exception
import java.util.*

class LocationUtils2 {
    var timer1: Timer? = null
    var lm: LocationManager? = null
    var locationCallBack: LocationCallBack? = null
    var context: Context? = null
    var gps_enabled = false
    var network_enabled = false
    fun getLocation(context: Context, callBack: LocationCallBack?): Int {
        //locationCallBack = callBack
        this.context = context
        if (lm == null) lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            gps_enabled = lm!!.isProviderEnabled(LocationManager.GPS_PROVIDER) //是否开启GPS
            if (!gps_enabled) {
                Log.e("location", "请开启GPS导航...") //开启导航服务才可以获取到位置
                // 返回开启GPS导航设置界面
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            context.startActivityForResult(intent, 0);
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            network_enabled = lm!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER) //没有google服务的手机,返回false
            if (!network_enabled) {
                Log.e("location", "无Google服务") //开启导航服务才会返回true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (!gps_enabled && !network_enabled) return 1
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return 2
        }
        if (gps_enabled) lm!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListenerGps)
        if (network_enabled) lm!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListenerNetwork)
        timer1 = Timer()
        timer1!!.schedule(GetLastLocation(), 20000)
        return 0
    }

    var locationListenerGps: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {

//            Log.i("66666", "location: GPS");
            timer1!!.cancel()
            locationCallBack!!.gotLocation(location)
            lm!!.removeUpdates(this)
            lm!!.removeUpdates(locationListenerNetwork)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }
    var locationListenerNetwork: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
//            Log.i("66666", "location: Network");
            timer1!!.cancel()
            locationCallBack!!.gotLocation(location)
            lm!!.removeUpdates(this)
            lm!!.removeUpdates(locationListenerGps)
        }

        override fun onProviderDisabled(provider: String) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
    }

    internal inner class GetLastLocation : TimerTask() {
        override fun run() {
            lm!!.removeUpdates(locationListenerGps)
            lm!!.removeUpdates(locationListenerNetwork)
            var net_loc: Location? = null
            var gps_loc: Location? = null
            val other_loc: Location? = null
            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            if (gps_enabled) gps_loc = lm!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (network_enabled) net_loc = lm!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.time > net_loc.time) locationCallBack!!.gotLocation(gps_loc) else locationCallBack!!.gotLocation(net_loc)
                return
            }
            if (gps_loc != null) {
                locationCallBack!!.gotLocation(gps_loc)
                return
            }
            if (net_loc != null) {
                locationCallBack!!.gotLocation(net_loc)
                return
            }
            locationCallBack!!.gotLocation(null)
        }
    }

    //获取到Location位置后回调
    interface LocationCallBack {
        fun gotLocation(location: Location?)
    }
}