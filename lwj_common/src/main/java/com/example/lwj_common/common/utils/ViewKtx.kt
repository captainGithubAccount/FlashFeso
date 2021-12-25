package com.example.flashfeso_lwj.flashfeso.utils

import android.app.Activity
import android.view.View

fun View.back(activity: Activity){
    this.setOnClickListener {
        activity.onBackPressed()
    }
}