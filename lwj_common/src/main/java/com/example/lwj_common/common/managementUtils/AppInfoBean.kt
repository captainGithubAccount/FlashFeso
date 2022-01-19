package com.example.lwj_common.common.managementUtils

import android.os.Parcelable
import android.os.Parcel
import com.example.lwj_common.common.managementUtils.AppInfoBean

class AppInfoBean() : Parcelable {
    @JvmField
    var appLabel: String? = null
    @JvmField
    var appVersion: String? = null
    @JvmField
    var firstInstallTime: Long = 0
    @JvmField
    var lastUpdateTime: Long = 0
    @JvmField
    var packageName: String? = null
    @JvmField
    var versionCode = 0
    @JvmField
    var versionName: String? = null

    constructor(parcel: Parcel) : this() {
        appLabel = parcel.readString()
        appVersion = parcel.readString()
        firstInstallTime = parcel.readLong()
        lastUpdateTime = parcel.readLong()
        packageName = parcel.readString()
        versionCode = parcel.readInt()
        versionName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(appLabel)
        parcel.writeString(appVersion)
        parcel.writeLong(firstInstallTime)
        parcel.writeLong(lastUpdateTime)
        parcel.writeString(packageName)
        parcel.writeInt(versionCode)
        parcel.writeString(versionName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppInfoBean> {
        override fun createFromParcel(parcel: Parcel): AppInfoBean {
            return AppInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<AppInfoBean?> {
            return arrayOfNulls(size)
        }
    }

}