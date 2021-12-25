package com.example.lwj_base.common.base

/**
 * 系统常量类
 * Create by 19/04/08
 */
object BaseConstants {
    const val TAG_ERROR = "TAG:NET_EXCEPTION"

    //
    const val ISLOG = true

    //debug开关
    const val debug = true

    //鉴权失败
    const val AUTHORITY_FALSE = "{\"code\":\"999\",\"msg\":\"鉴权失败\"}"

    //SharedPreferences key
    const val SHARED_PREFERENCES_KEY = "l_crediti_app"
    const val LOG_IN_OFFLINE = 4011

    /**
     * 防止连续点击两次的时间
     */
    const val DOUBLE_CLICK_TIME: Long = 500

    /**
     * 十天的毫秒数
     */
    const val TEN_DAY = (1000 * 60 * 60 * 24 * 10).toLong()

    /**
     * 包名
     */
    const val PACKAGE_NAME = "com.rs.flashpeso"

    //客户端类型
    const val CLIENT_TYPE = "android"
    const val EMPTY_STRING = "--"
    const val PAY_CHANNEL_STP = "STP"
    const val PAY_CHANNEL_OPENPAY = "OpenPay"
    const val PAY_CHANNEL_OXXO = "OXXO"

    //adjust_code_注册
    const val ADJUST_CODE_REGISTER = "g5w1vz"

    //adjust_code_首次放贷
    const val ADJUST_CODE_FIRST_LENDING = "ptj1mw"

    //adjust_code_首次申请贷款
    const val ADJUST_CODE_FIRST_APPLY = "699sjq"
}