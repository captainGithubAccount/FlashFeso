package com.example.flashfeso_lwj.flashfeso.utils

import android.content.SharedPreferences
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

class SharedPreferenceUtils @Inject constructor(
    val mSharedPreferences: SharedPreferences
){
    fun isFirstLuanch(): Boolean = mSharedPreferences.getBoolean("isFirstLuanch", true)

    fun setNotFirstLuanch() = with(mSharedPreferences.edit()){
        putBoolean("isFirstLuanch", false)
        apply()
    }

}