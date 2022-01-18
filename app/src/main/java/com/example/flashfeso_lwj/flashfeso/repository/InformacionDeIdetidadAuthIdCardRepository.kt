package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.InformacionDeldetidadService
import com.example.flashfeso_lwj.flashfeso.entity.AuthIdCardResponse
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class InformacionDeIdetidadAuthIdCardRepository @Inject constructor(
    private val service: InformacionDeldetidadService
): BaseRepository<Int>() {

    fun query(map: HashMap<String, Any>) = whenLauchInIO {
        val dataResult = service.getAuthIdCardResponse(map).getDataResult()
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