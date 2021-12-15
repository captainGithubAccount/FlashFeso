package com.example.flashfeso_lwj.flashfeso.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flashfeso_lwj.flashfeso.api.data.service.SplashService
import com.example.flashfeso_lwj.flashfeso.entity.VersionResponse
import com.example.flashfeso_lwj.flashfeso.utils.TAG_ERROR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


class SplashRepository @Inject constructor(
    private val splashService: SplashService,
): CoroutineScope by CoroutineScope(Dispatchers.IO) {

    protected val versionErrorLiveData = MutableLiveData<String>()

    //todo --
    protected val versionLiveData = MutableLiveData<VersionResponse>()

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

    //todo --
    fun getVersionLiveData(): LiveData<VersionResponse> = versionLiveData

    fun getVersionErrorLiveData(): LiveData<String> = versionErrorLiveData



}