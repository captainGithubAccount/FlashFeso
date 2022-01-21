package com.example.flashfeso_lwj.flashfeso.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lwj_base.common.base.BaseConstants.ISLOG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext
/**
* Create time: 12.27
*
* 注意事项: 需要在构造器传入协程上下文, 否则会出现类型强转错误
* 一个数据一个库
* */
abstract class BaseRepositoryPrevious<T>(override val coroutineContext: CoroutineContext = Dispatchers.IO) : CoroutineScope{
    val _dataLiveData = MutableLiveData<DataResult<T>>()
    fun getDataLiveData(): LiveData<DataResult<T>> = _dataLiveData

    inline fun onLauchInIO(crossinline block: suspend () -> Unit) = launch {
        try {
            block()
        }catch (e: Exception){
            _dataLiveData.postValue(DataResult.Error(errorMessage = e.message))
            if(ISLOG)Log.d("--REP ERROR MESSAGE", e.message.toString())
            e.printStackTrace()
        }
    }


}

