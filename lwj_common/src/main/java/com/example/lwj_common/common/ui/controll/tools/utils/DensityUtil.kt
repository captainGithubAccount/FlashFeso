package com.example.lwj_common.common.ui.controll.tools.utils

import android.content.Context

object DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.1f).toInt()
    }

    //private var displayMetrics: DisplayMetrics? = null
    fun px2dp(context: Context,px: Float): Float {
        return px / context.applicationContext.resources.displayMetrics?.density!! + .1f
    }

    fun getDisplayWidthDp(context: Context): Float{
        return px2dp(context,getDisplayWidthPixels(context).toFloat())
    }

    fun getDisplayWidthPixels(context: Context): Int{
        val displayMetrics =context.applicationContext.resources.displayMetrics
        return displayMetrics?.widthPixels!!
    }

    /*
    val displayWidthDp: Float
        get() = px2dp(displayWidthPixels.toFloat())
    val displayWidthPixels: Int
        get() = displayMetrics?.widthPixels!!

    init {
        displayMetrics = App.instance?.getResources()?.getDisplayMetrics()
    }*/
}