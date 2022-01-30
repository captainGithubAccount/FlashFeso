package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AppService
import com.example.flashfeso_lwj.flashfeso.entity.RepaymentOxxoEntity
import com.example.lwj_common.common.repository.BaseRepository
import java.lang.Exception
import javax.inject.Inject

class RepaymentOxxoRepository @Inject constructor(
    private val service: AppService
): BaseRepository<RepaymentOxxoEntity>() {
    override fun postErrorMessage(e: Exception) {

    }

    fun query(map: HashMap<String, Any>) = onLaunch {
        val resultState = service.getRepaymentOxxoResponse(map).getResultState()
        resultState.whenError {
            liveData.postValue(it)
        }
        resultState.whenSuccess {
            liveData.postValue(it)
        }
        resultState.whenClear {
            liveData.postValue(it)
        }
    }

}