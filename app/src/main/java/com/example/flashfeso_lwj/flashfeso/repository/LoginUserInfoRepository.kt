package com.example.flashfeso_lwj.flashfeso.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flashfeso_lwj.common.entity.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.LoginUserInfoService
import com.example.flashfeso_lwj.flashfeso.entity.UserInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class LoginUserInfoRepository @Inject constructor(
    private val service: LoginUserInfoService
): CoroutineScope by CoroutineScope(Dispatchers.IO) {

    private var _dataLiveData = MutableLiveData<DataResult<UserInfoEntity>>()
     fun getDataLiveData(): LiveData<DataResult<UserInfoEntity>> = _dataLiveData

    fun query(map: HashMap<String, Any>){

        launch {
            try {
                getDataFromService(map)
            }catch (e: Exception){
                _dataLiveData.postValue(DataResult.Error(errorMessage = e.message))
                e.printStackTrace()
            }
        }
    }
    private suspend fun getDataFromService(map: HashMap<String, Any>){
        val dataResult = service.getUserInfoDetail(map).getDataResult()
        dataResult.whenSuccess {
            it?.let{_dataLiveData.postValue(DataResult.Success(it))}
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
    }

}