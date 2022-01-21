package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.flashfeso.base.DataResult

data class AuthUserInfoResponse(
    val code: Int,
    val data: AuthUserInfoEntity?,
    val msg: String
){
    fun getDataResult(): DataResult<AuthUserInfoEntity> {
        if(code == 200 && data != null){
            return DataResult.Success(data)
        }else if(code != 200){
            return DataResult.Error(errorMessage = "返回码为${code}")
        }else{
            return DataResult.Error(errorMessage = "返回的数据为null")
        }
    }
}

data class AuthUserInfoEntity(
    val authAllin: Boolean,
    val days: String,
    val isAddressAuth: Boolean,
    val isBankAuth: Boolean,
    val isCardAuth: Boolean,
    val isContactsAuth: Boolean,
    val isEmployAuth: Boolean,
    val isLoanHisAuth: Boolean,
    val phone: String,
    val quota: String
)

