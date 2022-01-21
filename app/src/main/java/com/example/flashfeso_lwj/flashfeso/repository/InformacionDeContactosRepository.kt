package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.InformacionDeContactosService
import com.example.flashfeso_lwj.flashfeso.base.BaseRepositoryPrevious
import javax.inject.Inject

class InformacionDeContactosRepository @Inject constructor(
    private val service: InformacionDeContactosService
): BaseRepositoryPrevious<Int>() {

    fun query(map: HashMap<String, Any>) = onLauchInIO {
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