package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.InfomationAuthAddressService
import com.example.flashfeso_lwj.flashfeso.entity.AuthAddressResponse
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class InfomationAdressRepository @Inject constructor(
    private val service: InfomationAuthAddressService
): BaseRepository<Int>() {

    fun query(map: HashMap<String, Any>) = whenLauchInIO {
        val dataResult = service.getAuthAddressResponse(map).getDataResult()
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