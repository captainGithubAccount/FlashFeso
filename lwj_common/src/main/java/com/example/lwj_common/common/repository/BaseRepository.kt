package com.example.lwj_common.common.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lwj_base.common.base.BaseConstants
import com.example.lwj_common.common.net.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * alter time: 1.21
 *
 * 注意事项: 需要在构造器传入协程上下文, 否则会出现类型强转错误
 * 一个数据一个库, 因此需要创建多个库在项目中, (viewModel只1个, 命名为活动名去掉Atv后缀, XxxViewModel)
 * 库的命名(接口返回的实体类去掉Entity后缀, XxxRepository)
 * */
abstract class BaseRepository<T>(override val coroutineContext: CoroutineContext = Dispatchers.IO) : CoroutineScope  {

    var liveData = MutableLiveData<ResultState<T>>()
    private val _liveData: LiveData<ResultState<T>> get() = liveData
    fun getLiveData(): LiveData<ResultState<T>> = _liveData
    /**
     * block: 需要在后台操作的代码块(如通过retrofit获取后台数据)
     * */
    inline fun onLaunch(crossinline block: suspend () -> Unit ) = launch{
        try {
            block()
            //注意: 这里使用block.invoke()会报错
        }catch (e: Exception){
            postErrorMessage(e)
            if(BaseConstants.ISLOG) Log.e("--REP ERROR MESSAGE", e.message.toString())
            e.printStackTrace()
        }
    }


    /**
     * 当需要post异常时(在界面获取异常信息显示或处理)重写
     * _dataLiveData.postValue(DataResult.Error(errorMessage = e.message))
     * @param e 可能遇见的接口流程如retrofit等异常
     * */
    abstract fun postErrorMessage(e: Exception)
}