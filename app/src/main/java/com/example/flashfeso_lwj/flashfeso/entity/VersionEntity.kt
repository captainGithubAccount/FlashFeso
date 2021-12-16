package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.common.base.StateData

data class VersionResponse(
    val code: Int,
    val data: VersionEntity?,
    val msg: String
){
    fun getStateData(): StateData<VersionEntity>{
        return if(this.code == 200 && this.data != null){
            StateData.Success(data)
        }else if(this.code != 200){
            StateData.Error(errorMessage = "返回码为${code}")
        }else{
            StateData.Error(errorMessage = "返回的数据为null")
        }

        /*when(this.code){
            200 -> StateData.Success(data)
            else -> StateData.Error(errorMessage = "返回码为${code}")
        }*/
    }
}

data class VersionEntity(
    val VId: String,
    val VName: String,
    val downloadURl: String,
    val isUpdate: Boolean,
    val solveTProblem: String
)