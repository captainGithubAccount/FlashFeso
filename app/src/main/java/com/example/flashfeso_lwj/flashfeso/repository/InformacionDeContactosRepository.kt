package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.InformacionDeContactosService
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class InformacionDeContactosRepository @Inject constructor(
    private val service: InformacionDeContactosService
): BaseRepository<Int>() {

    fun query(map: HashMap<String, Any>) = whenLauchInIO {
        val dataResult = service.getDeContactosResponse(map).getDataResult()
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