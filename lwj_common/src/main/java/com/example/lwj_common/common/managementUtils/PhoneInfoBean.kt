package com.example.lwj_common.common.managementUtils

import android.os.Parcelable
import android.os.Parcel
import com.example.lwj_common.common.managementUtils.PhoneInfoBean

class PhoneInfoBean() : Parcelable {
    var displayName: String? = null
    var firstName: String? = null
    var middleName: String? = null
    var lastName: String? = null
    var companyName: String? = null
    var jobTitle: String? = null
    var phoneLabel: String? = null
    var phoneValue: String? = null

    constructor(parcel: Parcel) : this() {
        displayName = parcel.readString()
        firstName = parcel.readString()
        middleName = parcel.readString()
        lastName = parcel.readString()
        companyName = parcel.readString()
        jobTitle = parcel.readString()
        phoneLabel = parcel.readString()
        phoneValue = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(displayName)
        parcel.writeString(firstName)
        parcel.writeString(middleName)
        parcel.writeString(lastName)
        parcel.writeString(companyName)
        parcel.writeString(jobTitle)
        parcel.writeString(phoneLabel)
        parcel.writeString(phoneValue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhoneInfoBean> {
        override fun createFromParcel(parcel: Parcel): PhoneInfoBean {
            return PhoneInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<PhoneInfoBean?> {
            return arrayOfNulls(size)
        }
    }

}