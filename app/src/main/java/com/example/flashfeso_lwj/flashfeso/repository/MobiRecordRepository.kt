package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.InformacionDeldetidadMobiRecordService
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class MobiRecordRepository @Inject constructor(
    val service: InformacionDeldetidadMobiRecordService
): BaseRepository<Int>() {

    fun query(map: HashMap<String, Any>) = whenLauchInIO {
        val dataResult = service.getRecordResponse(map).getDataResult()
        dataResult.whenSuccessResponse {
            it?.run{_dataLiveData.postValue(this)}
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
        dataResult.whenClear {
            _dataLiveData.postValue(it)
        }
    }
}