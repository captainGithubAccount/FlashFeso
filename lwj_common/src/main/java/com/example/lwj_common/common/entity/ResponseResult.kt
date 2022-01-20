package com.example.lwj_common.common.entity

import android.util.Log
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.lwj_base.common.base.BaseConstants

sealed class ResponseResult<T> (
    val data: T? = null,
    val msg: String? = null
){
     class Clear<T>(data: T?, clearMessage: String? = null): ResponseResult<T>(data, clearMessage)
     class Success<T>( data: T,  successMessagle: String? = null): ResponseResult<T>(data, successMessagle)
     class Error<T>( data: T? = null,  errorMessage: String?): ResponseResult<T>(data, errorMessage)
     class Loading<T>( data: T? = null,  loadingMessage: String? = null): ResponseResult<T>(data, loadingMessage)
    inline fun whenSuccessOrError(blockSuccess: (T?) -> Unit, blockError: (ResponseResult<T>) -> Unit){
        when(this){
            is Success -> blockSuccess(data)
            is Error -> {
                blockError(this)
                if(BaseConstants.ISLOG) Log.e(BaseConstants.TAG_ERROR, this.msg!!)
            }
        }
    }

    //清空用户信息(全修改为"")用的
    inline fun whenClear(block: (ResponseResult<T>)->Unit){
        if(this is Clear){
            block.invoke(this)
        }
    }

    inline fun whenSuccessAndDefaultErrorDeal(blockSuccess: (T?) -> Unit){
        when(this){
            is Success -> blockSuccess(data)
            is Error -> {
                if(BaseConstants.ISLOG) Log.e(BaseConstants.TAG_ERROR, this.msg!!)
                //Toast.makeText(BaseApp.context, "error: ${this.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }

    }

    suspend inline fun whenSuccessAndSuspend(block: suspend (T?) -> Unit){
        //注意这里的block里代码应该是不耗时的
        if(this is Success){
            block(data)
        }
    }

    inline fun whenSuccess(block: (T?) -> Unit){
        //注意这里的block里代码应该是不耗时的
        if(this is Success){
            block(data)
        }
    }

    inline fun whenSuccessResponse(block: (ResponseResult<T>?) -> Unit){
        if(this is Success){
            block(this)
        }
    }

    inline fun whenError(block: (ResponseResult<T>) -> Unit){
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