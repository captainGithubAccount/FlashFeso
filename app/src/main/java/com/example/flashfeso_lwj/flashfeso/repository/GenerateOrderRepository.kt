package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.api.data.service.DetallesDeLosPrestamosService
import com.example.flashfeso_lwj.flashfeso.entity.GenerateOrderEntity
import com.example.lwj_common.common.repository.BaseRepository
import java.lang.Exception
import javax.inject.Inject

class GenerateOrderRepository @Inject constructor(
    private val service: DetallesDeLosPrestamosService
): BaseRepository<GenerateOrderEntity>() {

    fun query(map: HashMap<String, Any>) = onLaunch {
        val response = service.getGenerateOrderResponse(map).getResponse()
        response.whenSuccessResponse {
            liveData.postValue(it)
        }
        response.whenError {
            liveData.postValue(it)
        }
        response.whenClear {
            liveData.postValue(it)
        }

    }

    override fun postErrorMessage(e: Exception) {

    }
}