package com.example.flashfeso_lwj.flashfeso.api.data.service

import com.example.flashfeso_lwj.flashfeso.entity.MobiRecordResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface InformacionDeldetidadMobiRecordService {
    @POST("mexico/other/mobi/live/record")
    fun getRecordResponse(
        @Body map: HashMap<String, Any>
    ): MobiRecordResponse
}