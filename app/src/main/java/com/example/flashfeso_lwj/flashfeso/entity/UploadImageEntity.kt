package com.example.flashfeso_lwj.flashfeso.entity

data class UploadImageResponse(
    val code: Int,
    val data: UploadImageEntity?,
    val msg: String
)

data class UploadImageEntity(
    val fatherLastName: String?,
    val flag: String,
    val motherLastName: String,
    val name: String,
    val url: String
)