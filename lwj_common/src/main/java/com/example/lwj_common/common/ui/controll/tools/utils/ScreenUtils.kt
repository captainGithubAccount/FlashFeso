package com.example.lwj_common.common.ui.controll.tools.utils

import android.content.Context
import android.util.TypedValue

/**
 * Created by popfisher on 2016/8/19.
 */
object ScreenUtils {
    /**
     * 获取屏幕高度(px)
     */
    fun getScreenHeight(context: Context): Int {
        return context.getResources().getDisplayMetrics().heightPixels
    }

    /**
     * 获取屏幕宽度(px)
     */
    fun getScreenWidth(context: Context): Int {
        return context.getResources().getDisplayMetrics().widthPixels
    }

    fun dp2px(context: Context, dp: Float): Int {
        return (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.getResources().getDisplayMetrics()) + 0.5f).toInt()
    }
}