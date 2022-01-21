package com.example.flashfeso_lwj.flashfeso.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.InicioCurrDetailService
import com.example.flashfeso_lwj.flashfeso.entity.CurrDetailEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class InicioCurrDetailRepository2 @Inject constructor(
    private val service: InicioCurrDetailService
): CoroutineScope by CoroutineScope(Dispatchers.IO) {
    private val _dataLiveData = MutableLiveData<DataResult<CurrDetailEntity>>()
    fun getDataLiveData(): LiveData<DataResult<CurrDetailEntity>> = _dataLiveData

    fun query(map: HashMap<String, Any>) = launch {
        try {
            getDataFromService(map)
        }catch (e: Exception){
            _dataLiveData.postValue(DataResult.Error(errorMessage = e.message))
            e.printStackTrace()
        }
    }

    private suspend fun getDataFromService(map: HashMap<String, Any>) {


        val dataResult = service.getCurrDetails(map).getDataResult()
        dataResult.whenSuccess {
            it?.run{_dataLiveData.postValue(DataResult.Success(it))}
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
        dataResult.whenClear {
            _dataLiveData.postValue(it)
        }
    }
}