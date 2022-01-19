package com.example.flashfeso_lwj.flashfeso.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import com.example.flashfeso_lwj.base.entity.DataResult

import javax.inject.Inject

data class CurrDetailResponse(
    val code: Int,
    val data: CurrDetailEntity?,
    val msg: String,
) {

    fun getDataResult(): DataResult<CurrDetailEntity> {
        if (code == 200 && data != null) {
            return DataResult.Success(data)
        } else if (code != 200 && code != 4011) {
            return DataResult.Error(errorMessage = "返回码为${code}")
        } else if (code != 200 && code == 4011) {
            return DataResult.Clear(clearMessage = msg)
        } else {
            return DataResult.Error(errorMessage = "返回的数据为null")
        }
    }
}

fun a(){
    bb(3).c
}
data class bb(var c: Int)

data class CurrDetailEntity(
    val applyStatus: String?,
    val disburalAmount: String?,
    val interest: String?,
    val loanAmount: String?,
    val minAmount: String?,
    val orderStatus: Int,
    val processingFee: String?,
    val productId: Int,
    val repayDate: String?,
    val repaymentAmount: String?,
    val tenure: Int,
    val loanId: String? ,
    val transferTime: String?,
    val overdueDays: String? ,
    val overdueAmount: String? ,
    val rejectedReDate: String? ,
    val bankNo: String?,
    val bankName: String?,
    val repaidMoney: String? ,
    val remainderMoney: String?,
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(applyStatus)
        parcel.writeString(disburalAmount)
        parcel.writeString(interest)
        parcel.writeString(loanAmount)
        parcel.writeString(minAmount)
        parcel.writeInt(orderStatus)
        parcel.writeString(processingFee)
        parcel.writeInt(productId)
        parcel.writeString(repayDate)
        parcel.writeString(repaymentAmount)
        parcel.writeInt(tenure)
        parcel.writeString(loanId)
        parcel.writeString(transferTime)
        parcel.writeString(overdueDays)
        parcel.writeString(overdueAmount)
        parcel.writeString(rejectedReDate)
        parcel.writeString(bankNo)
        parcel.writeString(bankName)
        parcel.writeString(repaidMoney)
        parcel.writeString(remainderMoney)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CurrDetailEntity> {
        override fun createFromParcel(parcel: Parcel): CurrDetailEntity {
            return CurrDetailEntity(parcel)
        }

        override fun newArray(size: Int): Array<CurrDetailEntity?> {
            return arrayOfNulls(size)
        }
    }
}