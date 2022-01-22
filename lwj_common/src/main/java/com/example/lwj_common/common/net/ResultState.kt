package com.example.lwj_common.common.net

import android.util.Log
import com.example.lwj_base.common.base.BaseConstants

/*
* alter time: 1.21
* autor: lwj
* create reason: 每次网络数据都存在错误和成功状态, 每次都需要建多个livedata去返回错误信息, 而且当再添加其他状态又要多创建livedata
* alter time: 12.18
* alter content: 将原有构造方法和参数删除, 使的看起来更像枚举类, 便于观察
* */
sealed class ResultState<T>(
    val msg: String? = null,
    val data: T? = null
) {
    class Clear<T>(clearMessage: String? = null, data: T? = null) : ResultState<T>(clearMessage, data)
    class Success<T>(successMessagle: String? = null, data: T?) : ResultState<T>(successMessagle, data)
    class Error<T>(errorMessage: String?, data: T? = null) : ResultState<T>(errorMessage, data)
    class Loading<T>(loadingMessage: String? = null, data: T? = null) : ResultState<T>(loadingMessage, data)

    inline fun whenSuccessOrError(blockSuccess: (T?) -> Unit, blockError: (ResultState<T>) -> Unit) {
        when (this) {
            is Success -> blockSuccess(data)
            is Error -> {
                blockError(this)
                if (BaseConstants.ISLOG) Log.e(BaseConstants.TAG_ERROR, this.msg !!)
            }
        }
    }

    //清空用户信息(全修改为"")用的
    inline fun whenClear(block: (ResultState<T>) -> Unit) {
        if (this is Clear) {
            block.invoke(this)
        }
    }

    /**
     * 默认错误处理
     * */
    inline fun whenSuccessAndDefaultErrorDeal(blockSuccess: (T?) -> Unit) {
        when (this) {
            is Success -> blockSuccess(data)
            is Error -> {
                if (BaseConstants.ISLOG) Log.e(BaseConstants.TAG_ERROR, this.msg !!)
                //Toast.makeText(BaseApp.context, "error: ${this.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }

    }

    suspend inline fun whenSuccessAndSuspend(block: suspend (T?) -> Unit) {
        //注意这里的block里代码应该是不耗时的
        if (this is Success) {
            block(data)
        }
    }

    /**
    * 当只需要response中的data不需要msg时可用
    * */
    inline fun whenSuccessData(block: (T?) -> Unit) {
        //注意这里的block里代码应该是不耗时的
        if (this is Success) {
            block(data)
        }
    }

    inline fun whenSuccess(block: (ResultState<T>) -> Unit) {
        if (this is Success) {
            block(this)
        }
    }

    inline fun whenError(block: (ResultState<T>) -> Unit) {
        if (this is Error) {
            block(this)
        }
    }

    inline fun whenLoading(block: () -> Unit) {
        if (this is Loading) {
            block()
        }
    }
}