package com.example.lwj_common.common.ui.controll.tools.app_data

import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils

object AppInfo {
    var isFirstLaunch: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_FIRST_LOGIN, true)
        set(isFirstLogin) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_FIRST_LOGIN, isFirstLogin)

        }

    var token: String? = null
        get(){
            if(!field.isUseful()) SharedPreferenceUtils.getString(SharedPreferenceUtils.TOKEN, "")
            return field
        }
        set(value) {
            SharedPreferenceUtils.setString(SharedPreferenceUtils.TOKEN, value)
        }



    //是否是第一次使用
    var isFirstUse: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_FIRST_USE, true)
        set(isFirstUse) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_FIRST_USE, isFirstUse)
        }


}

