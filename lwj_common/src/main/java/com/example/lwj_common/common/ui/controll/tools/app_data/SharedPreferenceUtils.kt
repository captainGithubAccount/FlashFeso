package com.example.lwj_common.common.ui.controll.tools.app_data

import android.content.Context
import android.content.SharedPreferences
import com.example.lwj_base.common.base.BaseApp

/**
 * 注意, 使用前必须将应用app设置为继承自BaseApp的类, 否则BaseApp.appContext无法初始化获取
 * */
object SharedPreferenceUtils {
    private var _spInstance: SharedPreferences? = null
    init {
        _spInstance = BaseApp.appContext.getSharedPreferences(AppConstants.SP_CREATE_KEY,
                Context.MODE_PRIVATE)
    }



    /**
     * 是否第一次登录
     * */
    val IS_FIRST_LOGIN = "isFirstLogin"

    /**
     * 是否第一次试用
     */
    val IS_FIRST_USE = "isFirstUse"

    /**
     * 是否阅读了所有权限
     */
    val IS_READ_JURISDICTION = "isReadJurisdiction"

    /**
     * 是否同意隐私条款
     */
    val IS_AGREE_PRIVACY_PROTOCOL = "isAgreePrivacyProtocol"

    /**
     * 令牌
     */
    val TOKEN = "token"

    /**
     * 用户Id
     */
    val USER_ID = "userId"

    /**
     * 用户名称
     */
    val USER_NAME = "userName"

    /**
     * 密码
     */
    val PASSWORD = "password"

    /**
     * 账户
     */
    val ACCOUNT = "account"

    /**
     * 虚拟ID
     */
    val DUMMYID = "dummyId"

    /**
     * 渠道
     */
    val CHANNEL = "channel"

    /**
     * 当前经度
     */
    val LONGITUDE = "longitude"

    /**
     * 当前纬度
     */
    val LATITUDE = "latitude"

    /**
     * appInfo
     */
    val APP_INFO_TO_STRING = "appInfoToString"

    /**
     * deviceInfo
     */
    val DEVICE_INFO_TO_STRING = "deviceInfoToString"

    /**
     * contacts
     */
    val CONTACTS_TO_STRING = "contactsToString"

    /**
     * message
     */
    val MESSAGE_TO_STRING = "messageToString"

    /**
     * GPS_ADID
     */
    val GPS_ADID = "gpsAdid"

    /**
     * 所有认证是否完成
     */
    val AUTH_ALL_IN = "authAllin"

    /**
     * 住址信息是否认证
     */
    val IS_ADDRESS_AUTH = "isAddressAuth"

    /**
     * 工作信息是否认证
     */
    val IS_EMPLOY_AUTH = "isEmployAuth"

    /**
     * 借贷历史是否认证
     */
    val IS_LOAN_HIS_AUTH = "isLoanHisAuth"

    /**
     * 联系人信息是否认证
     */
    val IS_CONTACTS_AUTH = "isContactsAuth"

    /**
     * 证件信息是否认证
     */
    val IS_CARD_AUTH = "isCardAuth"

    /**
     * 银行卡信息是否认证
     */
    val IS_BANK_AUTH = "isBankAuth"

    /**
     * 首次放贷
     */
    val IS_FIRST_LENDING = "isFirstLending"

    /**
     * 首次申请贷款
     */
    val IS_FIRST_APPLY = "isFirstApply"

    /**
     * @param key
     * @param value
     * @return void
     * @throws
     * @Title: setString
     * @Description: 添加String类型的数据
     */
    fun setString(key: String, value: String?) {
        putObject(key, value)
    }

    /**
     * @param key
     * @param defValue
     * @return String
     * @throws
     * @Title: getString
     * @Description: 获取String类型的数据
     */
    fun getString(key: String?, defValue: String?): String? {
        return _spInstance?.getString(key, defValue)
    }

    /**
     * @param key
     * @param value
     * @return void
     * @throws
     * @Title: setBoolean
     * @Description: 添加boolean类型的数据
     */
    fun setBoolean(key: String, value: Boolean) {
        putObject(key, value)
    }

    /**
     * @param key
     * @param defValue
     * @return boolean
     * @throws
     * @Title: getBoolean
     * @Description: 获取boolean类型的数据
     */
    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return _spInstance?.getBoolean(key, defValue)!!
    }

    /**
     * @param key
     * @param value
     * @return void
     * @throws
     * @Title: setInt
     * @Description: 添加int型数据
     */
    fun setInt(key: String, value: Int) {
        putObject(key, value)
    }

    /**
     * @param key
     * @param defValue
     * @return int
     * @throws
     * @Title: getInt
     * @Description: 获取int型数据
     */
    fun getInt(key: String?, defValue: Int): Int {
        return _spInstance?.getInt(key, defValue)!!
    }

    /**
     * @param key
     * @param value
     * @return void
     * @throws
     * @Title: setLong
     * @Description: 添加int型数据
     */
    fun setLong(key: String, value: Long) {
        putObject(key, value)
    }

    /**
     * @param key
     * @param defValue
     * @return int
     * @throws
     * @Title: getLong
     * @Description: 获取int型数据
     */
    fun getLong(key: String?, defValue: Long): Long {
        return _spInstance?.getLong(key, defValue)!!
    }


    /**
     * @param key
     * @param value
     * @return void
     * @throws
     * @Title: putObject
     * @Description: 添加数据
     */
    private fun putObject(key: String, value: Any?) {
        if (value != null) {
            val editor = _spInstance?.edit()
            if (_spInstance?.contains(key)!!) {
                editor?.remove(key)
            }
            if (value is Boolean) {
                editor?.putBoolean(key, (value as Boolean?)!!)
            } else if (value is Float) {
                editor?.putFloat(key, (value as Float?)!!)
            } else if (value is Int) {
                editor?.putInt(key, (value as Int?)!!)
            } else if (value is Long) {
                editor?.putLong(key, (value as Long?)!!)
            } else if (value is String) {
                editor?.putString(key, value as String?)
            }
            editor?.apply()
        }
    }

    /**
     * @return void
     * @throws
     * @Title: clearAll
     * @Description: 清除所有数据
     */
    fun clearAll() {
        val editor = _spInstance?.edit()
        editor?.clear()
        editor?.apply()
    }

    /**
     * @param key
     * @return void
     * @throws
     * @Title: removeByKey
     * @Description: 根据key清除对应的数据
     */
    fun removeByKey(key: String?) {
        val editor = _spInstance?.edit()
        editor?.remove(key)
        editor?.apply()
    }

}


