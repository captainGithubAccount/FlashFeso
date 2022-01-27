package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AppService
import com.example.flashfeso_lwj.flashfeso.entity.ProSupportEntity
import com.example.lwj_common.common.repository.BaseRepository
import java.lang.Exception
import javax.inject.Inject

class ProSupportRepository @Inject constructor(
    private val service: AppService
): BaseRepository<ProSupportEntity>() {
    override fun postErrorMessage(e: Exception) {

    }

    fun query() = onLaunch {
        val resultState = service.getProSupportResponse().getResultState()
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