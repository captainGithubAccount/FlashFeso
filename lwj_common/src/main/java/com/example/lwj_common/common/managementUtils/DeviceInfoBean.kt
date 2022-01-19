package com.example.lwj_common.common.managementUtils

import android.os.Parcelable
import com.alibaba.fastjson.annotation.JSONField
import android.os.Parcel
import com.example.lwj_common.common.managementUtils.DeviceInfoBean

class DeviceInfoBean() : Parcelable {
    var applyChannel: String? = null
    @JvmField
    var androidId: String? = null

    @JSONField(name = "IMEI")
    var iMEI: String? = null
    @JvmField
    var uuid: String? = null
    @JvmField
    var generalDeviceId: String? = null
    @JvmField
    var idForAdvertising: String? = null
    @JvmField
    var idForVendor = 0
    @JvmField
    var country: String? = null
    @JvmField
    var carrierName: String? = null
    @JvmField
    var cpuCount = 0
    @JvmField
    var cpuSpeed: String? = null
    @JvmField
    var deviceArch: String? = null
    @JvmField
    var displayResolution: String? = null
    @JvmField
    var ipAddress: String? = null
    var isSimulator = false
    @JvmField
    var macAddress: String? = null
    @JvmField
    var model: String? = null
    @JvmField
    var brand: String? = null
    @JvmField
    var networkType: String? = null
    @JvmField
    var androidVersion: String? = null
    @JvmField
    var simSerialNumber: String? = null
    @JvmField
    var totalMemory: String? = null
    @JvmField
    var totalStorage: String? = null

    @JSONField(name = "RAM")
    var rAM: String? = null

    @JSONField(name = "ROM")
    var rOM: String? = null
    var longitude: String? = null
    var latitude: String? = null

    constructor(parcel: Parcel) : this() {
        applyChannel = parcel.readString()
        androidId = parcel.readString()
        iMEI = parcel.readString()
        uuid = parcel.readString()
        generalDeviceId = parcel.readString()
        idForAdvertising = parcel.readString()
        idForVendor = parcel.readInt()
        country = parcel.readString()
        carrierName = parcel.readString()
        cpuCount = parcel.readInt()
        cpuSpeed = parcel.readString()
        deviceArch = parcel.readString()
        displayResolution = parcel.readString()
        ipAddress = parcel.readString()
        isSimulator = parcel.readByte() != 0.toByte()
        macAddress = parcel.readString()
        model = parcel.readString()
        brand = parcel.readString()
        networkType = parcel.readString()
        androidVersion = parcel.readString()
        simSerialNumber = parcel.readString()
        totalMemory = parcel.readString()
        totalStorage = parcel.readString()
        rAM = parcel.readString()
        rOM = parcel.readString()
        longitude = parcel.readString()
        latitude = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(applyChannel)
        parcel.writeString(androidId)
        parcel.writeString(iMEI)
        parcel.writeString(uuid)
        parcel.writeString(generalDeviceId)
        parcel.writeString(idForAdvertising)
        parcel.writeInt(idForVendor)
        parcel.writeString(country)
        parcel.writeString(carrierName)
        parcel.writeInt(cpuCount)
        parcel.writeString(cpuSpeed)
        parcel.writeString(deviceArch)
        parcel.writeString(displayResolution)
        parcel.writeString(ipAddress)
        parcel.writeByte(if (isSimulator) 1 else 0)
        parcel.writeString(macAddress)
        parcel.writeString(model)
        parcel.writeString(brand)
        parcel.writeString(networkType)
        parcel.writeString(androidVersion)
        parcel.writeString(simSerialNumber)
        parcel.writeString(totalMemory)
        parcel.writeString(totalStorage)
        parcel.writeString(rAM)
        parcel.writeString(rOM)
        parcel.writeString(longitude)
        parcel.writeString(latitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeviceInfoBean> {
        override fun createFromParcel(parcel: Parcel): DeviceInfoBean {
            return DeviceInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<DeviceInfoBean?> {
            return arrayOfNulls(size)
        }
    }


}