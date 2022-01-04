package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.base.ui.controll.activity.BaseDbActivity
import com.example.flashfeso_lwj.flashfeso.api.data.service.HistorialCrediticioService
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class HistorialCreditcioRepository @Inject constructor(
    private val service: HistorialCrediticioService
): BaseRepository<Int>() {

    fun query(map: HashMap<String, Any>) = whenLauchInIO {
        val dataResult = service.getAuthLoanHistoryResponse(map).getDataResult()
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