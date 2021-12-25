package com.example.flashfeso_lwj.base.ui.controll.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.lwj_common.R

import com.gyf.immersionbar.ktx.immersionBar

abstract class BasePageStyleActivity<T: ViewBinding>: BaseDbActivity<T>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.color_main_background)//设置状态栏颜色
            keyboardEnable(true)  //解决软键盘与底部输入框冲突问题
            //statusBarDarkFont(true, 0.2f) //当白色背景状态栏遇到不能改变状态栏字体为深色的设备时,
            // 原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        }

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}