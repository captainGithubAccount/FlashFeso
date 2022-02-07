package com.example.flashfeso_lwj.flashfeso.entity

import android.os.Parcelable
import android.os.Parcel

class RepaymentEntity(): Parcelable {
    var clabe: String? = null
    var empresa: String? = null
    var bank: String? = null

    constructor(parcel: Parcel): this() {
        clabe = parcel.readString()
        empresa = parcel.readString()
        bank = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(clabe)
        parcel.writeString(empresa)
        parcel.writeString(bank)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<RepaymentEntity> {
        override fun createFromParcel(parcel: Parcel): RepaymentEntity {
            return RepaymentEntity(parcel)
        }

        override fun newArray(size: Int): Array<RepaymentEntity?> {
            return arrayOfNulls(size)
        }
    }


}