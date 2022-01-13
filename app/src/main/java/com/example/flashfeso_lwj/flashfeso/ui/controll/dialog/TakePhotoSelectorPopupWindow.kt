package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.PopupWindow
import com.example.flashfeso_lwj.flashfeso.event.CommonItemOnclickListener

class TakePhotoSelectorPopupWindow(
    private val mContext: Context,
    private val mListener: CommonItemOnclickListener
    ): PopupWindow() {

    init{
        val inflateContenetView = LayoutInflater.from(mContext)
    }
}