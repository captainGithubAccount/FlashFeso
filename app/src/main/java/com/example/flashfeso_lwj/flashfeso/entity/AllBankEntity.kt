package com.example.flashfeso_lwj.flashfeso.entity

import com.example.flashfeso_lwj.base.entity.DataResult

data class AllBankResponse (
    val code: Int,
    val data: List<AllBankEntity>,
    val msg: String
){
    fun getDataResult(): DataResult<List<AllBankEntity>> {
        if(code == 200){
            return DataResult.Success(data)
        }else if(code == 4011){
            return DataResult.Clear(clearMessage = msg)
        }else{
            return DataResult.Error(errorMessage = msg)
        }
    }
}


data class AllBankEntity(
    val code: Int,
    val name: String
)