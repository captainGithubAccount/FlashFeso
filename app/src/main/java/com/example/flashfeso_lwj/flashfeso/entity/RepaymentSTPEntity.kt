package com.example.flashfeso_lwj.flashfeso.entity

import android.os.Parcel
import android.os.Parcelable

/*data class RepaymentSTPResponse(
    val code: Int,
    val `data`: RepaymentSTPEntity,
    val msg: String
)*/

data class RepaymentSTPEntity(
    val bank: String?,
    val clabe: String?,
    val empresa: String?
): Parcelable {
    constructor(parcel: Parcel): this(parcel.readString(), parcel.readString(), parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bank)
        parcel.writeString(clabe)
        parcel.writeString(empresa)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<RepaymentSTPEntity> {
        override fun createFromParcel(parcel: Parcel): RepaymentSTPEntity {
            return RepaymentSTPEntity(parcel)
        }

        override fun newArray(size: Int): Array<RepaymentSTPEntity?> {
            return arrayOfNulls(size)
        }
    }
}