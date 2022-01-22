package com.example.flashfeso_lwj.flashfeso.entity

data class GenerateOrderResponse(
    val code: Int,
    val data: GenerateOrderEntity?,
    val msg: String
)

data class GenerateOrderEntity(
    val loanId: String,
    val phone: String,
    val times: Int,
    val userId: Int
)