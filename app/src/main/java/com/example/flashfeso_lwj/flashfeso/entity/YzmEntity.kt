package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.common.entity.DataResult

data class YzmEntity(
    val code: Int,
    val msg: String
){
    fun getDataResult(): DataResult<String> =
        when(code){
            200 -> DataResult.Success(msg)
            else -> DataResult.Error(errorMessage = msg)
        }

}