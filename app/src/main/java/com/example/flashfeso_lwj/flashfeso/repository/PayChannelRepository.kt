package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AppService
import com.example.lwj_common.common.repository.BaseRepository
import java.lang.Exception
import javax.inject.Inject

class PayChannelRepository @Inject constructor(
    private val service: AppService
): BaseRepository<ArrayList<String>>() {
    override fun postErrorMessage(e: Exception) {

    }

    fun query() = onLaunch {
        val resultState = service.getRepayChannelsResponse().getResultState()
        resultState.whenClear {
            liveData.postValue(it)
        }
        resultState.whenError {
            liveData.postValue(it)
        }
        resultState.whenSuccess {
            liveData.postValue(it)
        }

    }

}