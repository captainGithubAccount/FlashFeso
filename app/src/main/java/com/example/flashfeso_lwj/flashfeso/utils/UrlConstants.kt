package com.example.flashfeso_lwj.flashfeso.utils


object UrlConstants {

    //BASE_URL
    val BASE_URL = getBaseUrl()

    const val SEGURIDAD_DE_LOS_DATOS_URL = "https://api.flashpeso.com/website/privacypolicy.html"
    val UPLOAD_IMAGE_URL = "$BASE_URL/mexico/other/image"
    const val LOAN_POLICY_URL = "https://api.flashpeso.com/website/loanpolicy.html"

    private fun getBaseUrl(): String {
        return if (Constants.debug) {
            //测试
            //陈曦
            //            return "http:192.168.0.61:8082";
            //徐文亮
            //            return "http:192.168.0.18:8082";
            //佳林
            //            return "http:192.168.0.13:8082";
            "https://api.flashpeso.com"
        } else {
            "https://api.flashpeso.com"
        }
    }

}
