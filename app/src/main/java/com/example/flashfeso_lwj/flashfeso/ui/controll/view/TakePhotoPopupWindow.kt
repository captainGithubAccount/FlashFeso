package com.example.flashfeso_lwj.flashfeso.ui.controll.view

import android.app.Activity
import com.example.flashfeso_lwj.databinding.LayoutSelectorImageTypeWindowBinding
import com.example.lwj_common.common.ui.controll.event.CommonItemOnclickListener
import com.example.lwjtest_popupwindow.view.CommonPopupWindow

class TakePhotoPopupWindow(mContext: Activity): CommonPopupWindow<LayoutSelectorImageTypeWindowBinding>(mContext) {
    var mListener: CommonItemOnclickListener? = null
    fun setListener(listener: CommonItemOnclickListener){
        mListener = listener
    }
    override fun LayoutSelectorImageTypeWindowBinding.initView() {
        binding.selectorPopupWindowStringOne.setOnClickListener {
            mListener?.onItemOnclickListener(1)
        }

        binding.selectorPopupWindowStringTwo.setOnClickListener {
            mListener?.onItemOnclickListener(2)
        }

        binding.selectorPopupWindowStringThree.setOnClickListener {
            mListener?.onItemOnclickListener(3)
        }
    }

    override fun initPopupWindowSetting() {
        //设置弹出的窗口为满宽
        setMatchParent()
        //设置需要遮罩层
        setNeedMask()
        super.initPopupWindowSetting()
    }
}