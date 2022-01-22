package com.example.lwj_common.common.net

/**
* @author lwj
* 使用的时候可以继承该类新增返回字段, 如XxxAppBaseResponse
* 接口使用:
* @POST("mexico/generateOrder")
* suspend fun getGenerateOrderResponse(
* @Body map: HashMap<String, Any>
* ): XxxAppBaseResponse<GenerateOrderEntity>
*
* */
abstract class BaseResponse<T>(
    val code: Int,
    val data: T?,
    val msg: String?
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
}



/*  Demo:

abstract class FlashFesoAppBaseResponse<T>(
    code: Int ,
    data: T?,
    msg: String?,
    val dataId: Int,
    val totalCount: Int,
): BaseResponse<T>(code, data, msg)

    Use:

interface DetallesDeLosPrestamosService {
    @POST("mexico/generateOrder")
    suspend fun getGenerateOrderResponse(
        @Body map: HashMap<String, Any>
    ): FlashFesoAppBaseResponse<GenerateOrderEntity>
}


* */