package com.example.flashfeso_lwj.common.base

/*
* create time: 12.16
* autor: lwj
* create reson: 每次网络数据都存在错误和成功状态, 每次都需要建多个livedata去返回
* */
sealed class StateData<T>(
    val data: T? = null,
    val errorMessage: String? = null

) {
    class Success<T>(data: T?): StateData<T>(data)
    class Error<T>(data: T? = null, errorMessage: String?): StateData<T>(data,errorMessage)
    class Loading<T>(data: T? = null, errorMessage: String? = null): StateData<T>(data, errorMessage)

    inline fun whenSuccess(block: (T?) -> Unit){
        //注意这里的block应该是不耗时的
        if(this is Success){
            block(data)
        }
    }

    inline fun whenError(block: (StateData<T>) -> Unit){
        if(this is Error){
            block(this)
        }
    }

    inline fun whenLoading(block: () -> Unit){
        if(this is Loading){
            block()
        }
    }

}