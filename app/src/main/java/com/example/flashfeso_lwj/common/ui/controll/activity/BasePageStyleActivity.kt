package com.example.flashfeso_lwj.common.ui.controll.activity

import android.graphics.Rect
import android.os.Bundle
import android.widget.RadioButton
import androidx.viewbinding.ViewBinding
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.flashfeso.utils.DensityUtil
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.destroyImmersionBar
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

    //设置RadioButton的drawableTop大小, 防止底部导航图片位置错乱, 但是只要切图好的话是不会有这种情况的
    protected open fun setSize(rb: RadioButton) {
        val rect = Rect()
        //距离父窗体的距离，可以理解为左上和右下的坐标
        rect[0, 0, DensityUtil.dip2px(this, 20F)] =
            DensityUtil.dip2px(this, 20F)
        val drawables = rb.compoundDrawables
        //取出上边的图片设置大小
        drawables[1].bounds = rect
        //把这张图片放在上边，这四个参表示图片放在左、上、有、下
        rb.setCompoundDrawables(null, drawables[1], null, null)
    }
}