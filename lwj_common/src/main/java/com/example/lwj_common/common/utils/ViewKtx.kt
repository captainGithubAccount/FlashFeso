package com.example.flashfeso_lwj.flashfeso.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.lwj_base.common.base.BaseApp
import com.example.lwj_common.R
import com.example.lwj_common.common.utils.StringUtils

fun View.back(activity: Activity){
    this.setOnClickListener {
        activity.onBackPressed()
    }
}

fun View.textIsNotEmpty(): Boolean{
    return !StringUtils.isEmpty((this as TextView).text.toString().trim())
}

fun <T> T.addToast(context: Context ,toastString: String): T{
    return this.apply {
        Toast.makeText(context, toastString, Toast.LENGTH_LONG).show()
    }
}