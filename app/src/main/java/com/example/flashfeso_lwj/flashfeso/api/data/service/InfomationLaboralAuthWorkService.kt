package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.AuthWorkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface InfomationLaboralAuthWorkService {

    @POST("/mexico/authWork")
    suspend fun setAuthWork(@Body map: HashMap<String, Any>): AuthWorkResponse
}