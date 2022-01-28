package com.example.lwj_common.common.net

/**
 * @author lwj
 * 使用的时候可以参考该类或继承该类两种写法, 如XxxAppBaseResponse
 * 接口使用:
 * @POST("mexico/generateOrder")
 * suspend fun getGenerateOrderResponse(
 * @Body map: HashMap<String, Any>
 * ): XxxAppBaseResponse<GenerateOrderEntity>
 *
 * */
open class BaseResponse<T>(//或将open改为data
     val code: Int,
     val data: T?,
     val msg: String?,
    ) {
    fun getResultState(): ResultState<T> {
        if(code == 200) {
            return ResultState.Success(msg, data)
        } else if(code == 4011) {
            return ResultState.Clear(msg)
        } else {
            return ResultState.Error(msg)
        }
    }
}

/*  Demo(继承该类)

open class BaseResponse<T>(
     val code: Int,
     val data: T?,
     val msg: String?) {
    fun getResultState(): ResultState<T> {
        if(code == 200) {
            return ResultState.Success(msg, data)
        } else if(code == 4011) {
            return ResultState.Clear(msg)
        } else {
            return ResultState.Error(msg)
        }
    }
}


class FlashFesoAppBaseResponse<T>(
    code: Int,
    data: T?,
    msg: String?,
    val dataId: Int,
    val totalCount: Int,
): BaseResponse<T>(code, data, msg)
 */


/*  Demo(参考该类):

data class BaseResponse<T>(
     val code: Int,
     val data: T?,
     val msg: String?) {
    fun getResultState(): ResultState<T> {
        if(code == 200) {
            return ResultState.Success(msg, data)
        } else if(code == 4011) {
            return ResultState.Clear(msg)
        } else {
            return ResultState.Error(msg)
        }
    }
}

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
}

*/

/*
    Use:

interface DetallesDeLosPrestamosService {
    @POST("mexico/generateOrder")
    suspend fun getGenerateOrderResponse(
        @Body map: HashMap<String, Any>
    ): FlashFesoAppBaseResponse<GenerateOrderEntity>
}


* */