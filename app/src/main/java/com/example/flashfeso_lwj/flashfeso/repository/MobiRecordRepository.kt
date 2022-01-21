package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.InformacionDeldetidadService
import com.example.flashfeso_lwj.flashfeso.base.BaseRepository2
import javax.inject.Inject

class MobiRecordRepository @Inject constructor(
    val service: InformacionDeldetidadService
): BaseRepository2<Int>() {

    fun query(map: HashMap<String, Any>) = onLauchInIO {
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