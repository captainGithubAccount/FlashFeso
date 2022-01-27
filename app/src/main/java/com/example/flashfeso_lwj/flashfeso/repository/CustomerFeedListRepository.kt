package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.AppService
import com.example.flashfeso_lwj.flashfeso.entity.CustomerFeedListDetailEntity
import com.example.lwj_common.common.net.BaseArrayResponse
import com.example.lwj_common.common.repository.BaseRepository
import java.lang.Exception
import javax.inject.Inject

class CustomerFeedListRepository @Inject constructor(
    private val service: AppService
): BaseRepository<BaseArrayResponse<CustomerFeedListDetailEntity>>() {
    override fun postErrorMessage(e: Exception) {

    }

    fun query(map: HashMap<String, Any>) = onLaunch {
        val resultState = service.getCustomerFeedListResponse(map).getResultState()
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

