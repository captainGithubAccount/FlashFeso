package com.example.lwj_common.common.ui.controll.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.lwj_common.R
import com.example.lwj_common.common.ui.controll.tools.utils.DensityUtil

/**
 * Created by Cheng Bao on 2015/6/17.
 * 带删除图标的EditText
 */
class CommonClearEditText @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.editTextStyle,
) :
    AppCompatEditText(mContext, attrs, defStyle), OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null

    /**
     * 控件是否有焦点
     */
    private var hasFocus = false
    private fun init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_edittext_clear)
        }
        //自适应的宽高
        val intrinsicWidth = mClearDrawable?.getIntrinsicWidth()
        val intrinsicHeight = mClearDrawable?.getIntrinsicHeight()
        val i: Int = DensityUtil.dip2px(mContext, 20F)
        //固定死删除按钮宽高都是20dp,可改为自适应
        mClearDrawable?.setBounds(0, 0, i, i)
        //默认设置隐藏图标
        setClearIconVisible(false)
        //设置焦点改变的监听
        onFocusChangeListener = this
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mClearDrawable != null && event.action == MotionEvent.ACTION_UP) {
            val x = event.x.toInt()
            //判断触摸点是否在水平范围内
            val isInnerWidth = x > width - totalPaddingRight &&
                    x < width - paddingRight
            //获取删除图标的边界，返回一个Rect对象
            val rect = mClearDrawable!!.bounds
            //获取删除图标的高度
            val height = rect.height()
            val y = event.y.toInt()
            //计算图标底部到控件底部的距离
            val distance = (getHeight() - height) / 2
            //判断触摸点是否在竖直范围内(可能会有点误差)
            //触摸点的纵坐标在distance到（distance+图标自身的高度）之内，则视为点中删除图标
            val isInnerHeight = y > distance && y < distance + height
            if (isInnerHeight && isInnerWidth) {
                this.setText("")
            }
        }
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        // FIXME simple workaround to https://code.google.com/p/android/issues/detail?id=191430
        val startSelection = selectionStart
        val endSelection = selectionEnd
        if (startSelection < 0 || endSelection < 0) {
            Selection.setSelection(text as Spannable?, text!!.length)
        } else if (startSelection != endSelection) {
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                val text: CharSequence? = text
                setText(null)
                setText(text)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1],
            right, compoundDrawables[3])
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFocus = hasFocus
        if (hasFocus) {
            setClearIconVisible(text!!.length > 0)
        } else {
            setClearIconVisible(false)
        }
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int,
    ) {
        if (hasFocus) {
            setClearIconVisible(text.length > 0)
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable) {}

    init {
        init()
    }
}