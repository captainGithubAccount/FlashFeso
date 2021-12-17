package com.example.flashfeso_lwj.flashfeso.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flashfeso_lwj.common.base.StateData
import com.example.flashfeso_lwj.flashfeso.api.data.service.SplashService
import com.example.flashfeso_lwj.flashfeso.entity.VersionEntity
import com.example.flashfeso_lwj.flashfeso.entity.VersionResponse
import com.example.flashfeso_lwj.flashfeso.utils.Constants.TAG_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


class SplashRepository @Inject constructor(
    private val splashService: SplashService,
): CoroutineScope by CoroutineScope(Dispatchers.IO) {
    //todo 不带状态的数据实现方式
    private val versionErrorLiveData = MutableLiveData<String>()
    fun getVersionLiveData(): LiveData<VersionResponse> = versionLiveData
    fun getVersionErrorLiveData(): LiveData<String> = versionErrorLiveData
    private val versionLiveData = MutableLiveData<VersionResponse>()
    fun query() = launch {
        try {
            val versionResponse = splashService.getVersionLatest()
            versionLiveData.postValue(versionResponse)
        }catch (e: Exception){
            Log.e(TAG_ERROR,Log.getStackTraceString(e))
            versionErrorLiveData.postValue(e.message)
            e.printStackTrace()
        }

    }



    //todo 带有状态的数据实现方式(处理网络接口需要对错误信息捕获)
    private val dataLiveData = MutableLiveData<StateData<VersionEntity>>()
    fun getDataLiveData(): LiveData<StateData<VersionEntity>> = dataLiveData
    fun query2() = launch {
        try{
            // 数据类不抽取接口出来
             //val stateData = splashService.getVersionLatest().getStateData()

            //数据类抽取接口出来
            //val stateData = splashService.getVersionLatest().getStateData(data = splashService.getVersionLatest().data)
            val stateData = splashService.getVersionLatest().getStateData()
            stateData.whenSuccess {
                it?.run{dataLiveData.postValue(StateData.Success(it))}
            }

            stateData.whenError {
                 dataLiveData.postValue(it)

            }
        }catch (e: Exception){
            Log.e(TAG_ERROR,Log.getStackTraceString(e))
            dataLiveData.postValue(StateData.Error(errorMessage = e.message))
            e.printStackTrace()
        }
    }
}