package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.App
import com.example.flashfeso_lwj.flashfeso.api.data.service.AppService
import com.example.flashfeso_lwj.flashfeso.entity.RepaymentSTPEntity
import com.example.lwj_common.common.repository.BaseRepository
import java.lang.Exception
import javax.inject.Inject

class RepaymentSTPRepository @Inject constructor(private val service: AppService): BaseRepository<RepaymentSTPEntity>() {
    override fun postErrorMessage(e: Exception) {

    }

    fun query(map: HashMap<String, Any>) = onLaunch {
        val resultState = service.getRepaymentSTPResponse(map).getResultState()

        resultState.whenSuccess {
            liveData.postValue(it)
        }
        resultState.whenError {
            liveData.postValue(it)
        }
        resultState.whenClear {
            liveData.postValue(it)
        }
    }

}