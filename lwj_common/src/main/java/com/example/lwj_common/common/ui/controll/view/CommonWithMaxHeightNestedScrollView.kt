package com.example.lwj_common.common.ui.controll.view

import android.content.AttributionSource
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.core.widget.NestedScrollView

class CommonWithMaxHeightNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : NestedScrollView(context, attrs, defStyle) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /*
        1.精确模式（MeasureSpec.EXACTLY）
            在这种模式下，尺寸的值是多少，那么这个组件的长或宽就是多少。
        2.最大模式（MeasureSpec.AT_MOST）
            这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
        3.未指定模式（MeasureSpec.UNSPECIFIED）
            这个就是说，当前组件，可以随便用空间，不受限制
         */

        //设置最大高度400dp
        val height = MeasureSpec.makeMeasureSpec(dip2px(400), MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, height)
    }

    fun dip2px(dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5).toInt()
    }
}
