package com.example.flashfeso_lwj.common.base

sealed class Data<T>(
    val data: T? = null,
    val errorMessage: String? = null

) {
    class Success<T>(data: T?): Data<T>(data)
    class Error<T>(data: T? = null, errorMessage: String?): Data<T>(data,errorMessage)
    class Loading<T>(data: T? = null, errorMessage: String? = null): Data<T>(data, errorMessage)

    inline fun whenDataIsSuccess(block: (T?) -> Unit){
        //注意这里的block应该是不耗时的
        if(this is Success){
            block(data)
        }
    }

    suspend fun whenDataIsSuccessAndSuspend( block: suspend (T?) -> Unit){
        //注意这里的block应该是耗时的
        if(this is Success){
            block(data)
        }
    }

    inline fun whenDataIsError(block: (Data<T>) -> Unit){
        if(this is Error){
            block(this)
        }
    }

    inline fun whenDataIsLoading(block: () -> Unit){
        if(this is Loading){
            block()
        }
    }

}