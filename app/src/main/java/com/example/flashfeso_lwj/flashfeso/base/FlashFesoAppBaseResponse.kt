package com.example.flashfeso_lwj.flashfeso.base

import com.example.lwj_common.common.net.BaseResponse
import com.example.lwj_common.common.net.ResultState

class FlashFesoAppBaseResponse<T>(
    code: Int,
    data: T?,
    msg: String?,
    val dataId: Int,
    val totalCount: Int,
): BaseResponse<T>(code, data, msg)/*
data class FlashFesoAppBaseResponse<T>(
    val code: Int ,
     val data: T?,
     val msg: String?,
    val dataId: Int,
    val totalCount: Int,
){
    fun getResultState(): ResultState<T> {
        if (code == 200) {
            return ResultState.Success(msg, data)
        } else if (code == 4011) {
            return ResultState.Clear(msg)
        } else {
            return ResultState.Error(msg)
        }
    }
}*/
