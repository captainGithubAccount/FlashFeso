package com.example.lwj_common.common.ui.controll.tools.ktx

import android.widget.TextView
import androidx.viewbinding.ViewBinding

/*fun String.isUseful(): Boolean{
    return this.isNullOrBlank() && this == "null"
}*/

/**
 * 文本是否为空
 * return !(this.isNullOrBlank() || this == "null")
 * */
fun CharSequence?.isUseful(): Boolean{
    return !(this.isNullOrBlank() || this == "null")
}

/**
 * TextView文本是否为空
 * return !(this.isNullOrBlank() || this == "null")
 * */
fun TextView.isUseful(): Boolean {
    return this.text.isUseful()
}


/**
 * 将字符序列trim处理
 * return this?.run{toString().trim()}
 * */
fun CharSequence?.toTrim(): String?{
    return this?.run {
        toString().trim()
    }
}

/**
 * 将TextView字符序列trim处理
 * return this?.run{text.toTrim()}
 * */
fun TextView.toTrim(): String?{
    return this.run {
        text.toTrim()
    }
}



/**
* 删除文本中含有的空格
* */
fun CharSequence.deleteBlank(): String = this.toString().trim().replace(" ", "")

/*
fun main(){
    println("".deleteBlank())
    println("    adwahk dilawhf hdiwa    nawialg  ".deleteBlank())

    println("      wag      ".deleteBlank())
    val tipoDeCuentaPosition = 0
    val tarjeta = "n"
    println(tipoDeCuentaPosition == 0)
    println(tarjeta.isUseful())
    println(tarjeta.length != 18)
    if(tipoDeCuentaPosition == 0 && (!tarjeta.isUseful() || tarjeta.length != 18)){
        println("1进来了")
    }
    if(tipoDeCuentaPosition == 0 && !tarjeta.isUseful() || tarjeta.length != 18){
        println("2进来了")
    }
    if(tipoDeCuentaPosition == 0 && !tarjeta.isUseful() && tarjeta.length != 18){
        println("3进来了")
    }
    println(tipoDeCuentaPosition == 0 && !tarjeta.isUseful())
}
*/


