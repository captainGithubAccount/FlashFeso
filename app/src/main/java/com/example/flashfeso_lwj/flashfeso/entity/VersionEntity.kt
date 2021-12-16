package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.common.base.Data

data class VersionResponse(
    val code: Int,
    val data: VersionEntity?,
    val msg: String
){
    fun getWithStatusVersionResponse(): Data<VersionEntity>{
        return when(this.code){
            200 -> Data.Success(data)
            else -> Data.Error(errorMessage = "返回码为${code}")
        }

    }
}

data class VersionEntity(
    val VId: String,
    val VName: String,
    val downloadURl: String,
    val isUpdate: Boolean,
    val solveTProblem: String
)