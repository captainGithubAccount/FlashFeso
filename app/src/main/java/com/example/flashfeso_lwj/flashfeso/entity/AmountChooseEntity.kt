package com.example.flashfeso_lwj.flashfeso.entity


/*data class AmountChooseResponse(
    val code: Int,
    val `data`: AmountChooseEntity,
    val msg: String
)*/

data class AmountChooseEntity(
    val disburalAmount: Int,
    val gst: Int,
    val interest: Int,
    val loanAmount: Int,
    val minAmount: Int,
    val processingFee: Int,
    val repaymentAmount: Int,
    val serviceFee: Int,
    val tenure: Int
)