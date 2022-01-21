package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AgergarCuentaBancariaService
import com.example.flashfeso_lwj.flashfeso.base.BaseRepositoryPrevious
import javax.inject.Inject

class AuthBankInfoRepository @Inject constructor(
    private val service: AgergarCuentaBancariaService,
) : BaseRepositoryPrevious<Int>() {
    fun query(map: HashMap<String, Any>) = onLauchInIO {
        val dataResult = service.getAuthBankInfoResponse(map).getDataResult()
        dataResult.whenSuccessResponse {
            it?.run { _dataLiveData.postValue(this) }
        }
        dataResult.whenClear {
            _dataLiveData.postValue(it)
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
    }
}
