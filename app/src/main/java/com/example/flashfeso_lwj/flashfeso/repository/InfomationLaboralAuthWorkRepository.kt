package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.InfomationLaboralAuthWorkService
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class InfomationLaboralAuthWorkRepository @Inject constructor(
    private val service: InfomationLaboralAuthWorkService
): BaseRepository<Int>() {

    fun query(map: HashMap<String, Any>) = whenLauchInIO {
        val dataResult = service.setAuthWork(map).getDataResult()
        dataResult.whenSuccessResponse {
            it?.run { _dataLiveData.postValue(this) }
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
        dataResult.whenClear {
            _dataLiveData.postValue(it)
        }
    }

}