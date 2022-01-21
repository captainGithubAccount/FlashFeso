package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.AgergarCuentaBancariaService
import com.example.flashfeso_lwj.flashfeso.entity.AllBankEntity
import com.example.flashfeso_lwj.flashfeso.base.BaseRepository2
import javax.inject.Inject

class AllBankRepository @Inject constructor(
    val service: AgergarCuentaBancariaService
): BaseRepository2<List<AllBankEntity>>(){

    fun query() = onLauchInIO {
        val dataResult = service.getAllBank().getDataResult()
        dataResult.whenSuccess {
            if(it != null){
                _dataLiveData.postValue(DataResult.Success(it))
            }
        }
        dataResult.whenClear {
            _dataLiveData.postValue(it)
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
    }
}

