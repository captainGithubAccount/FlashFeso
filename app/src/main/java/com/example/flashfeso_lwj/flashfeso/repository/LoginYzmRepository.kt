package com.example.flashfeso_lwj.flashfeso.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.LoginYzmService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class LoginYzmRepository @Inject constructor(
    private val service: LoginYzmService
): CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private val _dataLiveData = MutableLiveData<DataResult<String>>()
     fun getDataLiveData(): LiveData<DataResult<String>> = _dataLiveData

    fun query(map: HashMap<String, Any>) = launch {
        try {
            getDataFromService(map)
        }catch (e: Exception){
            _dataLiveData.postValue(DataResult.Error(errorMessage = e.message))
            e.printStackTrace()
        }
    }

    suspend fun getDataFromService(map: HashMap<String, Any>){
        val dataResult = service.getVerificationCode(map).getDataResult()
        dataResult.whenSuccess {
            it?.run{_dataLiveData.postValue(DataResult.Success(it))}
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
    }



}