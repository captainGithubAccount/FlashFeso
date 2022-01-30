package com.example.flashfeso_lwj.flashfeso.entity

import android.os.Parcel
import android.os.Parcelable

/*data class OrderHistoryDataResponse(
    val code: Int,
    val `data`: List<OrderHistoryDataEntity>,
    val msg: String
)*/


data class OrderHistoryDataEntity(
    val applyNumber: String?,
    val createtime: String?,
    val id: String?,
    val interest: String?,
    val loanMoney: String?,
    val loanTerm: String?,
    val orderStatus: String?,
    val productId: String?,
    val remark: String?,
    val repayDate: String?,
    val repayMoney: String?,
    val serviceMoney: String?,
    val toAccountMoney: String?,
    val userId: String?,
    var overdueMoney: String? = null
): Parcelable {
    constructor(parcel: Parcel): this(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(applyNumber)
        parcel.writeString(createtime)
        parcel.writeString(id)
        parcel.writeString(interest)
        parcel.writeString(loanMoney)
        parcel.writeString(loanTerm)
        parcel.writeString(orderStatus)
        parcel.writeString(productId)
        parcel.writeString(remark)
        parcel.writeString(repayDate)
        parcel.writeString(repayMoney)
        parcel.writeString(serviceMoney)
        parcel.writeString(toAccountMoney)
        parcel.writeString(userId)
        parcel.writeString(overdueMoney)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<OrderHistoryDataEntity> {
        override fun createFromParcel(parcel: Parcel): OrderHistoryDataEntity {
            return OrderHistoryDataEntity(parcel)
        }

        override fun newArray(size: Int): Array<OrderHistoryDataEntity?> {
            return arrayOfNulls(size)
        }
    }
}