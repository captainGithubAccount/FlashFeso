package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.DeContactosResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface InformacionDeContactosService {

    @POST("mexico/authContactPerson")
    suspend fun getDeContactosResponse(
        @Body map: HashMap<String, Any>
    ): DeContactosResponse
}