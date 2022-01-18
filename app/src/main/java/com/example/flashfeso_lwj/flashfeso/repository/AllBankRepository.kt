package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AgergarCuentaBancariaService
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class AllBankRepository @Inject constructor(
    val service: AgergarCuentaBancariaService
): BaseRepository<Int>(){

    fun query() = whenLauchInIO {
        val dataResult = service.getAllBank().getDataResult()
        dataResult.whenSuccessResponse {
            it?.run{_dataLiveData.postValue(this)}
        }
        dataResult.whenClear {
            _dataLiveData.postValue(it)
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
    }
}

