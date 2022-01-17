package com.example.flashfeso_lwj.flashfeso.api.data.net


import okhttp3.*
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/*
* 注意: 使用okhttp之前应该先进行网络安全配置, 详情见我的博客 (关于Android 9.0以上的网络安全配置)
*
*
* */
object FileImageUpload2 {
    private const val mImageType = "multipart/form-data"

    /**
     * @param paramType 与后台约定好的接口Post(请求头中)请求参数对应的值
     * @param file     创建RequestBody(请求体)所需要的上传的文件
     * @param fileName 上传文件对应的文件名（例如：xxxx.png)[可随便写]
     */
    fun uploadImage(
        paramType: String?,
        file: File?,
        fileName: String?,
        callBack: uploadImageCallBack?
    ) {
//        ----------- 我的写法----------
        val requestBody2: RequestBody = Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("location", paramType)
            .addFormDataPart("file",
                fileName,
                RequestBody.create(parse.parse("image/jpg; charset=utf-8"), file))
            .build()
        //        -------------end ---------------
        val requestBody = Builder()
        requestBody.addFormDataPart("location", paramType)


        //2.创建RequestBody
        val fileBody: RequestBody =
            RequestBody.create(parse.parse("image/jpg; charset=utf-8"), file)

        //3.构建MultipartBody
        requestBody.addFormDataPart("file", fileName, fileBody)
            .setType(MultipartBody.FORM)
        val build: MultipartBody = requestBody.build()

//todo        ------------------------------------------

        //4.构建请求
        val uploadImageUrl: String = AppConfig.UPLOAD_IMAGE_URL
        val request: Request = Builder()
            .url(uploadImageUrl)
            .addHeader("userToken", InfoUtil.getToken())
            .addHeader("userId", InfoUtil.getUserId())
            .post(build) //requestBody2
            .build()
        val client: OkHttpClient = Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()


        //5.发送异步请求(同步请求会阻塞Android主线程, 使用同步请求的话许开辟一个子线程在该子线程里面调用该同步请求, 否则会报错, 因为同步请求会在当前所在的线程执行)
        client.newCall(request).enqueue(object : Callback {
            //使用enqueue方法表示异步发送一个请求, 同步请求则把enqueue方法改为execute()方法即可
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callBack?.onUPloadFailure(0x01, "请求失败")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val data = response.body!!.string()
                if (!StringUtils.isEmpty(data)) {
                    try {
                        val uploadReceiveBaseBean: SystemUploadBean =
                            JsonUtil.parseObject(data, SystemUploadBean::class.java)
                        if (uploadReceiveBaseBean != null && StringUtils.equals("200",
                                uploadReceiveBaseBean.getCode()) && !StringUtils.isEmpty(
                                uploadReceiveBaseBean.getData())
                        ) {
                            callBack?.onUploadSuccess(uploadReceiveBaseBean.getData(), paramType)
                        } else {
                            if (callBack != null) {
                                if (uploadReceiveBaseBean != null && !StringUtils.isEmpty(
                                        uploadReceiveBaseBean.getMsg())
                                ) {
                                    callBack.onUPloadFailure(0x05, uploadReceiveBaseBean.getMsg())
                                } else {
                                    callBack.onUPloadFailure(0x02, "上传失败，请重试")
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        callBack?.onUPloadFailure(0x03, "上传失败，请重试")
                    }
                } else {
                    callBack?.onUPloadFailure(0x04, "上传失败，请重试")
                }
            }
        })
    }

    /**
     * 上传文件回调接口
     */
    interface uploadImageCallBack {
        fun onUploadSuccess(data: String?, type: String?)
        fun onUPloadFailure(code: Int, info: String?)
    }
}