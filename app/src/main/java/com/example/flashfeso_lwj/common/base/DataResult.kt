package com.example.flashfeso_lwj.common.base

import android.util.Log
import android.widget.Toast
import com.example.flashfeso_lwj.App

/*
* create time: 12.16
* autor: lwj
* create reason: 每次网络数据都存在错误和成功状态, 每次都需要建多个livedata去返回错误信息, 而且当再添加其他状态又要多创建livedata
* alter time: 12.18将原有构造方法和参数删除, 使的看起来更像枚举类, 便于观察
* */
sealed class DataResult<T>{
    data class Success<T>(val data: T): DataResult<T>()
    data class Error<T>(val data: T? = null, val errorMessage: String?): DataResult<T>()
    data class Loading<T>(val data: T? = null, val errorMessage: String? = null): DataResult<T>()
    inline fun whenSuccessOrError(blockSuccess: (T?) -> Unit, blockError: (DataResult<T>) -> Unit){
        when(this){
            is Success -> blockSuccess(data)
            is Error -> {
                blockError(this)
                Log.e("StateData.Error message", this.errorMessage!!)
            }
        }
    }

    inline fun whenSuccessAndDefaultErrorDeal(blockSuccess: (T?) -> Unit){
        when(this){
            is Success -> blockSuccess(data)
            is Error -> {
                Log.e("StateData.Error message", this.errorMessage!!)
                Toast.makeText(App.context, "error: ${this.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }

    }

    inline fun whenSuccess(block: (T?) -> Unit){
        //注意这里的block里代码应该是不耗时的
        if(this is Success){
            block(data)
        }
    }

    inline fun whenError(block: (DataResult<T>) -> Unit){
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

