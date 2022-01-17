package com.example.flashfeso_lwj.flashfeso.api.data.net

import com.example.flashfeso_lwj.flashfeso.entity.UploadImageEntity
import com.example.flashfeso_lwj.flashfeso.entity.UploadImageResponse
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.UrlConstants
import com.example.lwj_common.common.ui.controll.tools.ktx.fromJson
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object ImageFileUpload {

    val uploadUrl: String = "${UrlConstants.BASE_URL}/mexico/other/image"


    /**
     * @param locationRequestParamValue 与后台约定好的接口Post(请求头中)请求参数对应的值
     * @param file     创建RequestBody(请求体)所需要的上传的文件
     * @param fileName 上传文件对应的文件名（例如：xxxx.png)[可随便写]
     */
    fun uploadImage(locationRequestParamValue: String, fileName: String, file: File, callBack: UploadImageCallBack){


//        构建RequestBody
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("location", locationRequestParamValue)
            .addFormDataPart("file", fileName, RequestBody.create("image/jpg; charset=utf-8".toMediaTypeOrNull(), file))
            .build()

//        构建请求
        val request: Request = Request.Builder()
            .url(uploadUrl)
            .addHeader("userToken", InfoUtil.getToken()!!)
            .addHeader("userId", InfoUtil.getUserId()!!)
            .post(requestBody)
            .build()

//        构建okhttp client并异步执行请求(注意:使用OkHttpClient.Builder()构建, 而不是直接OkHttpClient()创建, 好处: 创建的时候默认添加了很多默认配置)
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callBack.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body.toString()
                val response = jsonData.fromJson(UploadImageResponse::class.java)
                if(response.code == 200 && response.data != null && StringUtils.isEmpty(response.data.toString())){
                    callBack.onSuccess(response.data)
                }else{

                }
            }

        })




    }

    /*
    * 上传回掉接口
    * */
    interface UploadImageCallBack{
        fun onSuccess(data: UploadImageEntity)
        fun onError()
    }
}