package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.base.entity.DataResult

data class AuthAddressResponse(
    val code: Int,
    val msg: String
){
    fun getDataResult(): DataResult<Int>{
        if (code == 200) {
            return DataResult.Success(code, msg)
        } else if (code != 200 && code != 4011) {
            return DataResult.Error(errorMessage = "返回码为${code}")
        } else if (code != 200 && code == 4011) {
            return DataResult.Clear(clearMessage = msg)
        } else {
            return DataResult.Error(errorMessage = "返回的数据为null")
        }
    }
}