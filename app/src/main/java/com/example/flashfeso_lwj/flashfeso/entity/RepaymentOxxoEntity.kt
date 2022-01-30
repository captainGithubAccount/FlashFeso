package com.example.flashfeso_lwj.flashfeso.entity

/*data class RepaymentOxxoResponse(
    val code: Int,
    val `data`: RepaymentOxxoEntity,
    val msg: String
)*/

data class RepaymentOxxoEntity(
    val barcode: String,
    val barcode_url: String,
    val serviceMoney: String
)