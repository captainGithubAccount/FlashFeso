package com.example.flashfeso_lwj.flashfeso.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flashfeso_lwj.App
import com.example.flashfeso_lwj.common.entity.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.SplashVersionService
import com.example.flashfeso_lwj.flashfeso.entity.VersionEntity
import com.example.flashfeso_lwj.flashfeso.entity.VersionResponse
import com.example.flashfeso_lwj.flashfeso.utils.Constants.TAG_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


class SplashVersionRepository @Inject constructor(
    private val splashService: SplashVersionService,
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
            if(App.ISDEBUG)Log.e(TAG_ERROR,Log.getStackTraceString(e))
            versionErrorLiveData.postValue(e.message)
            e.printStackTrace()
        }

    }



    //todo 带有状态的数据实现方式(处理网络接口需要对错误信息捕获)
    private val dataLiveData = MutableLiveData<DataResult<VersionEntity>>()
     fun getDataLiveData(): LiveData<DataResult<VersionEntity>> = dataLiveData
    fun query2() = launch {
        try{
            getDataFromService()
        }catch (e: Exception){
            if(App.ISDEBUG)Log.e(TAG_ERROR,Log.getStackTraceString(e))
            dataLiveData.postValue(DataResult.Error(errorMessage = e.message))
            e.printStackTrace()
        }
    }

    suspend fun getDataFromService(){
        val dataResult = splashService.getVersionLatest().getDataResult()
        dataResult.whenSuccess {
            it?.run{dataLiveData.postValue(DataResult.Success(it))}
        }

        dataResult.whenError {
            dataLiveData.postValue(it)
        }
    }
}