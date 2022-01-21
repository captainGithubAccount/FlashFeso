package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.flashfeso.base.DataResult

data class RiskInfoResponse(
    val code: Int,
    val msg: String
){
    fun getDataResult(): DataResult<Int> {
        if(code == 200){
            return DataResult.Success(code, msg)
        }else if(code == 4011){
            return DataResult.Clear(clearMessage = msg)
        }else{
            return DataResult.Error(errorMessage = msg)
        }
    }
}