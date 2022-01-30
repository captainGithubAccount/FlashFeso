package com.example.flashfeso_lwj.flashfeso.entity

import android.os.Parcelable
import android.os.Parcel

data class PayChannelEntity(
    var isSelector: Boolean = false,
    var payItem: String? = null
): Parcelable {
    constructor(parcel: Parcel): this(parcel.readByte() != 0.toByte(), parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if(isSelector) 1 else 0)
        parcel.writeString(payItem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<PayChannelEntity> {
        override fun createFromParcel(parcel: Parcel): PayChannelEntity {
            return PayChannelEntity(parcel)
        }

        override fun newArray(size: Int): Array<PayChannelEntity?> {
            return arrayOfNulls(size)
        }
    }
}