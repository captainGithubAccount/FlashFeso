package com.example.flashfeso_lwj.flashfeso.utils

import android.content.Context
import android.util.DisplayMetrics
import com.example.flashfeso_lwj.App

object DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.1f).toInt()
    }

    private var displayMetrics: DisplayMetrics? = null
    fun px2dp(px: Float): Float {
        return px / displayMetrics?.density!! + .1f
    }

    val displayWidthDp: Float
        get() = px2dp(displayWidthPixels.toFloat())
    val displayWidthPixels: Int
        get() = displayMetrics?.widthPixels!!

    init {
        displayMetrics = App.instance?.getResources()?.getDisplayMetrics()
    }
}