package com.example.flashfeso_lwj.base.ui.controll.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import kotlin.jvm.JvmOverloads
import com.example.flashfeso_lwj.R
import android.widget.TextView
import android.widget.RelativeLayout
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View

/**
 * 简单进度对话框(转圈圈)
 */
class SimpleProgressDialog @JvmOverloads constructor(
    context: Context?,
    theme: Int = R.style.sp_simple_dialog
) : Dialog(
    context!!,
    theme
) {
    var isCanCancel = false

    /**
     * 提示文字
     */
    private val tv_progress_msg: TextView?
    private val rl_container: RelativeLayout

    constructor(context: Context?, isCanCancel: Boolean) : this(context, R.style.sp_simple_dialog) {
        this.isCanCancel = isCanCancel
    }

    constructor(context: Context?, progressMsg: String?) : this(context, R.style.sp_simple_dialog) {
        if (!TextUtils.isEmpty(progressMsg)) {
            rl_container.setBackgroundResource(R.drawable.shape_simple_progress_dialog_bg)
            tv_progress_msg!!.text = progressMsg
            tv_progress_msg.visibility = View.VISIBLE
        } else {
            rl_container.setBackgroundColor(Color.parseColor("#00FFFFFF"))
            tv_progress_msg!!.visibility = View.GONE
        }
    }

    fun setProgressMsg(progressMsg: CharSequence?) {
        if (tv_progress_msg != null) {
            tv_progress_msg.text = progressMsg
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isCanCancel) {
                dismiss()
            } else {
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /*
    * setCancelable(false)
    * setCanceledOnTouchOutside(false)
    *
    * 当这两个方法都设置为false后, 点击外部位置和按返回键都不能取消dialog对话框
    * 通过dismiss()方法可以取消dialog
    * 这时候需要自己重写事件监听(onKeyEvent)方法
    * */
    init {
        setContentView(R.layout.dialog_simpleprogressdialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        tv_progress_msg = findViewById<View>(R.id.tv_progress_msg) as TextView
        rl_container = findViewById<View>(R.id.rl_container) as RelativeLayout
    }
}