package com.example.flashfeso_lwj.flashfeso.api.data.net

import com.example.flashfeso_lwj.flashfeso.entity.UploadImageEntity
import com.example.flashfeso_lwj.flashfeso.entity.UploadImageResponse
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.UrlConstants
import com.example.lwj_common.common.ui.controll.tools.ktx.fromJson
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import com.google.gson.JsonSyntaxException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import java.io.File
import java.io.IOException
import java.lang.Exception
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
            .addFormDataPart("file", fileName, file.asRequestBody("image/jpg; charset=utf-8".toMediaTypeOrNull()))
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
                callBack.onError(0x01, "请求失败")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonData = response.body.toString()
                if(!StringUtils.isEmpty(jsonData)){
                    try {
                        val responseEntity = jsonData.fromJson(UploadImageResponse::class.java)
                        if(response.code == 200 && responseEntity.data != null && StringUtils.isEmpty(responseEntity.data.toString())){
                            callBack.onSuccess(responseEntity.data, locationRequestParamValue)
                        }else{
                            if(responseEntity.data != null && !StringUtils.isEmpty(responseEntity.msg)){
                                callBack.onError(0x05, "上传失败，请重试")
                            }else{
                                callBack.onError(0x02, "上传失败，请重试")
                            }
                        }
                    }catch (e: JsonSyntaxException){
                        e.printStackTrace()
                        callBack.onError(0x03, "上传失败，请重试")
                    }

                }else{
                    callBack.onError(0x04, "上传失败，请重试")
                }

            }

        })

    }

    /*
    * 上传回掉接口
    * */
    interface UploadImageCallBack{
        fun onSuccess(data: UploadImageEntity, locationRequestParamValue: String)
        fun onError(code: Int, info: String)
    }
}