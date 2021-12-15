package com.example.flashfeso_lwj.flashfeso.utils;


/**
 * 系统常量类
 * Create by 19/04/08
 */
public final class Constants {


    //debug开关
    public static final boolean debug = false;


    //鉴权失败
    public static final String AUTHORITY_FALSE = "{\"code\":\"999\",\"msg\":\"鉴权失败\"}";

    //SharedPreferences key
    public static final String SHARED_PREFERENCES_KEY = "l_crediti_app";


    public static final int LOG_IN_OFFLINE = 4011;

    /**
     * 防止连续点击两次的时间
     */
    public static final long DOUBLE_CLICK_TIME = 500;

    /**
     * 十天的毫秒数
     */
    public static final long TEN_DAY = 1000 * 60 * 60 * 24 * 10;

    /**
     * 包名
     */
    public static final String PACKAGE_NAME = "com.rs.flashpeso";

    //客户端类型
    public static final String CLIENT_TYPE = "android";


    public static final String EMPTY_STRING = "--";


    public static final String PAY_CHANNEL_STP = "STP";

    public static final String PAY_CHANNEL_OPENPAY = "OpenPay";

    public static final String PAY_CHANNEL_OXXO = "OXXO";

    //adjust_code_注册
    public static final String ADJUST_CODE_REGISTER = "g5w1vz";

    //adjust_code_首次放贷
    public static final String ADJUST_CODE_FIRST_LENDING = "ptj1mw";

    //adjust_code_首次申请贷款
    public static final String ADJUST_CODE_FIRST_APPLY = "699sjq";
}
