package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AppService
import com.example.flashfeso_lwj.flashfeso.entity.AmountChooseEntity
import com.example.lwj_common.common.repository.BaseRepository
import java.lang.Exception
import javax.inject.Inject

class AmountChooseRepository @Inject constructor(
    private val service: AppService
) : BaseRepository<AmountChooseEntity>() {
    override fun postErrorMessage(e: Exception) {

    }

    fun query(map: HashMap<String, Any>) = onLaunch {
        val resultState = service.getAmountChoose(map).getResultState()
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