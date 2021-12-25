package com.example.flashfeso_lwj.base.utils

import com.example.flashfeso_lwj.flashfeso.utils.SharedPreferenceUtils
import com.example.lwj_common.common.utils.StringUtils
import javax.inject.Inject

class InfoUtil @Inject constructor(
    private var mSharedPrefsUtil: SharedPreferenceUtils

){
    //用户id
    private var userId: String? = null

    //用户名称
    private var userName: String? = null

    //token
    private var token: String? = null

    //密码
    private var password: String? = null

    //账户
    private var account: String? = null

    //虚拟账户唯一识别码
    var dummyId: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.DUMMYID, "")
            }
            return field
        }
        set(dummyId) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.DUMMYID, dummyId)
        }

    //渠道
    var channel: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.CHANNEL, "")
            }
            return field
        }
        set(channel) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.CHANNEL, channel)
        }

    //当前经度
    var longitude: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.LONGITUDE, "")
            }
            return field
        }
        set(longitude) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.LONGITUDE, longitude)
        }

    //当前纬度
    var latitude: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.LATITUDE, "")
            }
            return field
        }
        set(latitude) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.LATITUDE, latitude)
        }

    //AppInfo
    var appInfoToString: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.APP_INFO_TO_STRING, "")
            }
            return field
        }
        set(appInfoToString) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.APP_INFO_TO_STRING, appInfoToString)
        }

    //deviceInfo
    var deviceInfoToString: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.DEVICE_INFO_TO_STRING, "")
            }
            return field
        }
        set(deviceInfoToString) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.DEVICE_INFO_TO_STRING, deviceInfoToString)
        }

    //contacts
    var contactsToString: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.CONTACTS_TO_STRING, "")
            }
            return field
        }
        set(contactsToString) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.CONTACTS_TO_STRING, contactsToString)
        }

    //message
    var messageToString: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.MESSAGE_TO_STRING, "")
            }
            return field
        }
        set(messageToString) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.MESSAGE_TO_STRING, messageToString)
        }
    var gpsAdid: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = mSharedPrefsUtil.getString(mSharedPrefsUtil.GPS_ADID, "")
            }
            return field
        }
        set(gpsAdid) {
            field = ""
            mSharedPrefsUtil.setString(mSharedPrefsUtil.GPS_ADID, gpsAdid)
        }

    //是否是第一次使用
    var isFirstUse: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_FIRST_USE, true)
        set(isFirstUse) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_FIRST_USE, isFirstUse)
        }

    //所有认证是否完成
    var authAllin: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.AUTH_ALL_IN, false)
        set(authAllin) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.AUTH_ALL_IN, authAllin)
        }

    //住址信息是否认证
    var isAddressAuth: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_ADDRESS_AUTH, false)
        set(isAddressAuth) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_ADDRESS_AUTH, isAddressAuth)
        }

    //工作信息是否认证
    var isEmployAuth: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_EMPLOY_AUTH, false)
        set(isEmployAuth) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_EMPLOY_AUTH, isEmployAuth)
        }

    //借贷历史是否认证
    var isLoanHisAuth: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_LOAN_HIS_AUTH, false)
        set(isLoanHisAuth) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_LOAN_HIS_AUTH, isLoanHisAuth)
        }

    //联系人信息是否认证
    var isContactsAuth: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_CONTACTS_AUTH, false)
        set(isContactsAuth) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_CONTACTS_AUTH, isContactsAuth)
        }

    //证件信息是否认证
    var isCardAuth: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_CARD_AUTH, false)
        set(isCardAuth) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_CARD_AUTH, isCardAuth)
        }

    //银行卡信息是否认证
    var isBankAuth: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_BANK_AUTH, false)
        set(isBankAuth) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_BANK_AUTH, isBankAuth)
        }

    fun setUserId(userId: String?) {
        this.userId = ""
        mSharedPrefsUtil.setString(mSharedPrefsUtil.USER_ID, userId)
    }

    fun getUserId(): String? {
        if (StringUtils.isEmpty(userId)) {
            userId = mSharedPrefsUtil.getString(mSharedPrefsUtil.USER_ID, "-1")
        }
        return userId
    }

    fun setUserName(userName: String?) {
        this.userName = ""
        mSharedPrefsUtil.setString(mSharedPrefsUtil.USER_NAME, userName)
    }

    fun getUserName(): String? {
        if (StringUtils.isEmpty(userName)) {
            userName = mSharedPrefsUtil.getString(mSharedPrefsUtil.USER_NAME, "-1")
        }
        return userName
    }

    fun setToken(token: String?) {
        this.token = ""
        mSharedPrefsUtil.setString(mSharedPrefsUtil.TOKEN, token)
    }

    fun getToken(): String? {
        if (StringUtils.isEmpty(token)) {
            token = mSharedPrefsUtil.getString(mSharedPrefsUtil.TOKEN, "")
        }
        return token
    }

    fun setPassword(password: String?) {
        this.password = ""
        mSharedPrefsUtil.setString(mSharedPrefsUtil.PASSWORD, password)
    }

    fun getPassword(): String? {
        if (StringUtils.isEmpty(password)) {
            password = mSharedPrefsUtil.getString(mSharedPrefsUtil.PASSWORD, "")
        }
        return password
    }

    fun setAccount(account: String?) {
        this.account = ""
        mSharedPrefsUtil.setString(mSharedPrefsUtil.ACCOUNT, account)
    }

    fun getAccount(): String? {
        if (StringUtils.isEmpty(account)) {
            account = mSharedPrefsUtil.getString(mSharedPrefsUtil.ACCOUNT, "")
        }
        return account
    }

    var isFirstLending: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_FIRST_LENDING, true)
        set(isFirstLending) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_FIRST_LENDING, isFirstLending)
        }
    var isFirstApply: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_FIRST_APPLY, true)
        set(isFirstApply) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_FIRST_APPLY, isFirstApply)
        }
    val isLogin: Boolean
        get() = !StringUtils.isEmpty(getToken())
    var isReadJurisdiction: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_READ_JURISDICTION, false)
        set(isReadJurisdiction) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_READ_JURISDICTION, isReadJurisdiction)
        }
    var isAgreePrivacyProtocol: Boolean
        get() = mSharedPrefsUtil.getBoolean(mSharedPrefsUtil.IS_AGREE_PRIVACY_PROTOCOL, false)
        set(isAgreePrivacyProtocol) {
            mSharedPrefsUtil.setBoolean(mSharedPrefsUtil.IS_AGREE_PRIVACY_PROTOCOL,
                isAgreePrivacyProtocol)
        }

    fun clearUserId() {
        mSharedPrefsUtil.removeByKey(mSharedPrefsUtil.USER_ID)
        userId = ""
    }

    fun clearAccount() {
        mSharedPrefsUtil.removeByKey(mSharedPrefsUtil.ACCOUNT)
        account = ""
    }

    fun clearToken() {
        mSharedPrefsUtil.removeByKey(mSharedPrefsUtil.TOKEN)
        token = ""
    }

    fun clearPassword() {
        mSharedPrefsUtil.removeByKey(mSharedPrefsUtil.PASSWORD)
        password = ""
    }

    fun clearUserName() {
        mSharedPrefsUtil.removeByKey(mSharedPrefsUtil.USER_NAME)
        userName = ""
    }

    fun clear() {
        clearUserId()
        userId = ""
        clearAccount()
        account = ""
        clearToken()
        token = ""
        clearPassword()
        password = ""
        clearUserName()
        userName = ""
    }
}
