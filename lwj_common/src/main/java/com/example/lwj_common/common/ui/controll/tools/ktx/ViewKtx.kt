package com.example.flashfeso_lwj.flashfeso.utils

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.Selection
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils

/**
 * 对回退箭头(view)进行onBackPressed监听
 * @param activity 需执行onBackPressed方法的活动上下文
 * */
fun View.back(activity: Activity){
    this.setOnClickListener {
        activity.onBackPressed()
    }
}

fun View.textIsEmpty(): Boolean{
    return StringUtils.isEmpty((this as TextView).text.toString().trim())
}

/**
 * 给任何对象添加吐司
 * @param context 吐司上下文
 * @param toastString 吐司提示的内容
 * @sample toastExample
 * */
fun <T> T.addToast(context: Context ,toastString: String): T{
    return this.apply {
        Toast.makeText(context, toastString, Toast.LENGTH_LONG).show()
    }
}
fun toastExample(context: Context){
    "test".addToast(context, "吐司内容")
}






