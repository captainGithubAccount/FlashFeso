package com.example.flashfeso_lwj.flashfeso.utils

import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils

object InfoUtil{

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
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.DUMMYID, "")
            }
            return field
        }
        set(dummyId) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.DUMMYID, dummyId)
        }

    //渠道
    var channel: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.CHANNEL, "")
            }
            return field
        }
        set(channel) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.CHANNEL, channel)
        }

    //当前经度
    var longitude: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.LONGITUDE, "")
            }
            return field
        }
        set(longitude) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.LONGITUDE, longitude)
        }

    //当前纬度
    var latitude: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.LATITUDE, "")
            }
            return field
        }
        set(latitude) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.LATITUDE, latitude)
        }


    //AppInfo
    var appInfoToString: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.APP_INFO_TO_STRING, "")
            }
            return field
        }
        set(appInfoToString) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.APP_INFO_TO_STRING, appInfoToString)
        }

    //deviceInfo
    var deviceInfoToString: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.DEVICE_INFO_TO_STRING, "")
            }
            return field
        }
        set(deviceInfoToString) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.DEVICE_INFO_TO_STRING, deviceInfoToString)
        }

    //contacts
    var contactsToString: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.CONTACTS_TO_STRING, "")
            }
            return field
        }
        set(contactsToString) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.CONTACTS_TO_STRING, contactsToString)
        }

    //message
    var messageToString: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.MESSAGE_TO_STRING, "")
            }
            return field
        }
        set(messageToString) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.MESSAGE_TO_STRING, messageToString)
        }
    var gpsAdid: String? = null
        get() {
            if (StringUtils.isEmpty(field)) {
                field = SharedPreferenceUtils.getString(SharedPreferenceUtils.GPS_ADID, "")
            }
            return field
        }
        set(gpsAdid) {
            field = ""
            SharedPreferenceUtils.setString(SharedPreferenceUtils.GPS_ADID, gpsAdid)
        }

    //是否是第一次使用
    var isFirstUse: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_FIRST_USE, true)
        set(isFirstUse) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_FIRST_USE, isFirstUse)
        }

    //所有认证是否完成
    var authAllin: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.AUTH_ALL_IN, false)
        set(authAllin) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.AUTH_ALL_IN, authAllin)
        }

    //住址信息是否认证
    var isAddressAuth: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_ADDRESS_AUTH, false)
        set(isAddressAuth) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_ADDRESS_AUTH, isAddressAuth)
        }

    //工作信息是否认证
    var isEmployAuth: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_EMPLOY_AUTH, false)
        set(isEmployAuth) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_EMPLOY_AUTH, isEmployAuth)
        }

    //借贷历史是否认证
    var isLoanHisAuth: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_LOAN_HIS_AUTH, false)
        set(isLoanHisAuth) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_LOAN_HIS_AUTH, isLoanHisAuth)
        }

    //联系人信息是否认证
    var isContactsAuth: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_CONTACTS_AUTH, false)
        set(isContactsAuth) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_CONTACTS_AUTH, isContactsAuth)
        }

    //证件信息是否认证
    var isCardAuth: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_CARD_AUTH, false)
        set(isCardAuth) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_CARD_AUTH, isCardAuth)
        }

    //银行卡信息是否认证
    var isBankAuth: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_BANK_AUTH, false)
        set(isBankAuth) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_BANK_AUTH, isBankAuth)
        }

    fun setUserId(userId: String?) {
        this.userId = ""
        SharedPreferenceUtils.setString(SharedPreferenceUtils.USER_ID, userId)
    }

    fun getUserId(): String? {
        if (StringUtils.isEmpty(userId)) {
            userId = SharedPreferenceUtils.getString(SharedPreferenceUtils.USER_ID, "-1")
        }
        return userId
    }

    fun setUserName(userName: String?) {
        this.userName = ""
        SharedPreferenceUtils.setString(SharedPreferenceUtils.USER_NAME, userName)
    }

    fun getUserName(): String? {
        if (StringUtils.isEmpty(userName)) {
            userName = SharedPreferenceUtils.getString(SharedPreferenceUtils.USER_NAME, "-1")
        }
        return userName
    }

    fun setToken(token: String?) {
        this.token = ""
        SharedPreferenceUtils.setString(SharedPreferenceUtils.TOKEN, token)
    }

    fun getToken(): String? {
        if (StringUtils.isEmpty(token)) {
            token = SharedPreferenceUtils.getString(SharedPreferenceUtils.TOKEN, "")
        }
        return token
    }

    fun setPassword(password: String?) {
        this.password = ""
        SharedPreferenceUtils.setString(SharedPreferenceUtils.PASSWORD, password)
    }

    fun getPassword(): String? {
        if (StringUtils.isEmpty(password)) {
            password = SharedPreferenceUtils.getString(SharedPreferenceUtils.PASSWORD, "")
        }
        return password
    }

    fun setAccount(account: String?) {
        this.account = ""
        SharedPreferenceUtils.setString(SharedPreferenceUtils.ACCOUNT, account)
    }

    fun getAccount(): String? {
        if (StringUtils.isEmpty(account)) {
            account = SharedPreferenceUtils.getString(SharedPreferenceUtils.ACCOUNT, "")
        }
        return account
    }

    var isFirstLending: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_FIRST_LENDING, true)
        set(isFirstLending) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_FIRST_LENDING, isFirstLending)
        }
    var isFirstApply: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_FIRST_APPLY, true)
        set(isFirstApply) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_FIRST_APPLY, isFirstApply)
        }
    val isLogin: Boolean
        get() = !StringUtils.isEmpty(getToken())
    var isReadJurisdiction: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_READ_JURISDICTION, false)
        set(isReadJurisdiction) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_READ_JURISDICTION, isReadJurisdiction)
        }
    var isAgreePrivacyProtocol: Boolean
        get() = SharedPreferenceUtils.getBoolean(SharedPreferenceUtils.IS_AGREE_PRIVACY_PROTOCOL, false)
        set(isAgreePrivacyProtocol) {
            SharedPreferenceUtils.setBoolean(SharedPreferenceUtils.IS_AGREE_PRIVACY_PROTOCOL,
                isAgreePrivacyProtocol)
        }

    fun clearUserId() {
        SharedPreferenceUtils.removeByKey(SharedPreferenceUtils.USER_ID)
        userId = ""
    }

    fun clearAccount() {
        SharedPreferenceUtils.removeByKey(SharedPreferenceUtils.ACCOUNT)
        account = ""
    }

    fun clearToken() {
        SharedPreferenceUtils.removeByKey(SharedPreferenceUtils.TOKEN)
        token = ""
    }

    fun clearPassword() {
        SharedPreferenceUtils.removeByKey(SharedPreferenceUtils.PASSWORD)
        password = ""
    }

    fun clearUserName() {
        SharedPreferenceUtils.removeByKey(SharedPreferenceUtils.USER_NAME)
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
