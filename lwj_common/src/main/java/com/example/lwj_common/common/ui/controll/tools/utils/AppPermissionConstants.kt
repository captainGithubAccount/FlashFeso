package com.example.flashfeso_lwj.flashfeso.utils



val APP_PERMISSIONS: Array<String> = arrayOf(
    "android.permission.INTERNET", //联网权限
    "android.permission.WRITE_EXTERNAL_STORAGE",  //写SD卡权限
    "android.permission.READ_EXTERNAL_STORAGE",  //读SD卡权限
    "android.permission.READ_CONTACTS",  //读取联系人的权限
    "android.permission.READ_PHONE_STATE",  //读取电话状态的权限
    "android.permission.ACCESS_NETWORK_STATE",//获取网络状态
    "android.permission.ACCESS_WIFI_STATE",//允许程序访问Wi-Fi网络状态信息
    "android.permission.ACCESS_FINE_LOCATION",//精确位置的权限,真机中使用的权限
    "android.permission.ACCESS_COARSE_LOCATION",//大概位置的权限,真机使用的权限
    "android.permission.CAMERA",//允许访问摄像头进行拍照
    "android.permission.WRITE_CONTACTS",//写入联系人，但不可读取
    "android.permission.READ_SMS")//允许程序读取短信息
const val PERMISSION_REQUEST_CODE = 1

