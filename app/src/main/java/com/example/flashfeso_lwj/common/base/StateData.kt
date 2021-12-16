package com.example.flashfeso_lwj.common.base

import android.util.Log
import android.widget.Toast
import com.example.flashfeso_lwj.App

/*
* create time: 12.16
* autor: lwj
* create reason: 每次网络数据都存在错误和成功状态, 每次都需要建多个livedata去返回错误信息, 而且当再添加其他状态又要多创建livedata
* */
sealed class StateData<T>(
    val data: T? = null,
    val errorMessage: String? = null

) {
    class Success<T>(data: T): StateData<T>(data)
    class Error<T>(data: T? = null, errorMessage: String?): StateData<T>(data,errorMessage)
    class Loading<T>(data: T? = null, errorMessage: String? = null): StateData<T>(data, errorMessage)
    inline fun whenSuccessOrError(blockSuccess: (T?) -> Unit, blockError: (StateData<T>) -> Unit){
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
                Toast.makeText(App.context, "error: ${this.errorMessage!!}", Toast.LENGTH_LONG).show()
            }
        }

    }

    inline fun whenSuccess(block: (T?) -> Unit){
        //注意这里的block里代码应该是不耗时的
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