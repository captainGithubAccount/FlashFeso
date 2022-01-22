package com.example.flashfeso_lwj.flashfeso.entity


/*data class AmountChooseResponse(
    val code: Int,
    val `data`: AmountChooseEntity,
    val msg: String
)*/

data class AmountChooseEntity(
        val disburalAmount: String,
        val gst: String,
        val interest: String,
        val loanAmount: String,
        var minAmount: String,
        val processingFee: String,
        val repaymentAmount: String,
        val serviceFee: String,
        val tenure: String
)