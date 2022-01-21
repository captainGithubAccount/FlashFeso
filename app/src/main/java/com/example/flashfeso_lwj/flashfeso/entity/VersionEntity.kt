package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.flashfeso.base.DataResult

data class VersionResponse(
    val code: Int,
    val data: VersionEntity?,
    val msg: String
){
    fun getDataResult(): DataResult<VersionEntity> {
        return if(this.code == 200 && this.data != null){
            DataResult.Success(data)
        }else if(this.code != 200){
            DataResult.Error(errorMessage = "返回码为${code}")
        }else{
            DataResult.Error(errorMessage = "返回的数据为null")
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