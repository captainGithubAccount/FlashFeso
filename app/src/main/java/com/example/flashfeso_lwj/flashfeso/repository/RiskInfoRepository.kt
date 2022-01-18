package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AgergarCuentaBancariaService
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class RiskInfoRepository @Inject constructor(
    private val service: AgergarCuentaBancariaService
): BaseRepository<Int>() {

    fun query(map: HashMap<String, Any>) = whenLauchInIO {
        val dataResult = service.getRiskInfoResponse(map).getDataResult()
        dataResult.whenSuccessResponse {
            it?.run {
                _dataLiveData.postValue(this)
            }
        }
        dataResult.whenError { _dataLiveData.postValue(it) }
    }
}