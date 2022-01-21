package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AgergarCuentaBancariaService
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class AuthBankInfoRepository @Inject constructor(
    private val service: AgergarCuentaBancariaService,
) : BaseRepository<Int>() {
    fun query(map: HashMap<String, Any>) = whenLauchInIO {
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
