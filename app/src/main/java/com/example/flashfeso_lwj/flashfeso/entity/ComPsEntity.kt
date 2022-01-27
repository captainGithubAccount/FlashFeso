package com.example.flashfeso_lwj.flashfeso.entity

/*data class ComPsResponse(
    val code: Int,
    val `data`: List<ComPsEntity>,
    val msg: String
)*/

data class ComPsEntity(
    val answer: String?,
    val createtime: String?,
    val id: Int?,
    val question: String?,
    val status: Int?,
    val updatetime: String?,
    var isOpen: Boolean = false
)