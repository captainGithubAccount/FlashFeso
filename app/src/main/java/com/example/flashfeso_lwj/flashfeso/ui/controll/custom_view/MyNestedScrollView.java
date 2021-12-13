package com.example.flashfeso_lwj.flashfeso.ui.controll.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.core.widget.NestedScrollView;

public class MyNestedScrollView extends NestedScrollView {
    public MyNestedScrollView(Context context) {
        this(context, null);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*
        1.精确模式（MeasureSpec.EXACTLY）
            在这种模式下，尺寸的值是多少，那么这个组件的长或宽就是多少。
        2.最大模式（MeasureSpec.AT_MOST）
            这个也就是父组件，能够给出的最大的空间，当前组件的长或宽最大只能为这么大，当然也可以比这个小。
        3.未指定模式（MeasureSpec.UNSPECIFIED）
            这个就是说，当前组件，可以随便用空间，不受限制
         */

        //设置最大高度400dp
        int height = MeasureSpec.makeMeasureSpec(dip2px(400), MeasureSpec.AT_MOST);
        Log.d("---", String.valueOf(height));
        super.onMeasure(widthMeasureSpec, height);
    }

    public int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
}
