package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.common.entity.DataResult

data class UserInfoResponse(
    val code: Int,
    val data: UserInfoEntity?,
    val msg: String
){
    fun getDataResult(): DataResult<UserInfoEntity>{
        return if(code == 200 && data != null){
            DataResult.Success(data, msg)
        }else if(code != 200){
            DataResult.Error(errorMessage = "返回码为${code}")
        }else{
            DataResult.Error(errorMessage = "返回的数据为null")
        }

    }
}

data class UserInfoEntity(
    val isRegis: Int,
    val phone: String,
    val token: String,
    val userId: Int
)