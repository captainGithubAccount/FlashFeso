package com.example.lwj_common.common.managementUtils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.Timer;
import java.util.TimerTask;

public class LocationUtils {
    Timer timer1;
    LocationManager lm;
    LocationCallBack locationCallBack;
    Context context;
    boolean gps_enabled = false;
    boolean network_enabled = false;

    public int getLocation(Context context, LocationCallBack callBack) {
        locationCallBack = callBack;
        this.context = context;
        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);//是否开启GPS
            if (!gps_enabled) {
                Log.e("location", "请开启GPS导航...");//开启导航服务才可以获取到位置
                // 返回开启GPS导航设置界面
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            context.startActivityForResult(intent, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);//没有google服务的手机,返回false
            if (!network_enabled) {
                Log.e("location", "无Google服务");//开启导航服务才会返回true
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!gps_enabled && !network_enabled)
            return 1;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return 2;
        }
        if (gps_enabled)
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if (network_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
        timer1 = new Timer();
        timer1.schedule(new GetLastLocation(), 20000);
        return 0;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {

//            Log.i("66666", "location: GPS");
            timer1.cancel();
            locationCallBack.gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
//            Log.i("66666", "location: Network");
            timer1.cancel();
            locationCallBack.gotLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            lm.removeUpdates(locationListenerGps);
            lm.removeUpdates(locationListenerNetwork);
            Location net_loc = null, gps_loc = null, other_loc = null;
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (gps_enabled)
                gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (network_enabled)
                net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime())
                    locationCallBack.gotLocation(gps_loc);
                else
                    locationCallBack.gotLocation(net_loc);
                return;
            }
            if (gps_loc != null) {
                locationCallBack.gotLocation(gps_loc);
                return;
            }
            if (net_loc != null) {
                locationCallBack.gotLocation(net_loc);
                return;
            }
            locationCallBack.gotLocation(null);
        }
    }

    //获取到Location位置后回调
    public interface LocationCallBack {
        void gotLocation(Location location);
    }
}

