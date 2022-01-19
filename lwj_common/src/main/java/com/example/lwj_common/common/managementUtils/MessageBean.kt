package com.example.lwj_common.common.managementUtils

import android.os.Parcelable
import android.os.Parcel
import com.example.lwj_common.common.managementUtils.MessageBean

class MessageBean() : Parcelable {
    //接收时间
    var messageDateTime: String? = null

    //类型
    var type: String? = null

    //对方地址
    var address: String? = null

    //对方名称
    var name: String? = null

    //短信内容
    var messageContent: String? = null

    constructor(parcel: Parcel) : this() {
        messageDateTime = parcel.readString()
        type = parcel.readString()
        address = parcel.readString()
        name = parcel.readString()
        messageContent = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(messageDateTime)
        parcel.writeString(type)
        parcel.writeString(address)
        parcel.writeString(name)
        parcel.writeString(messageContent)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MessageBean> {
        override fun createFromParcel(parcel: Parcel): MessageBean {
            return MessageBean(parcel)
        }

        override fun newArray(size: Int): Array<MessageBean?> {
            return arrayOfNulls(size)
        }
    }

}