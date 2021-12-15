package com.example.flashfeso_lwj.flashfeso.entity

data class VersionResponse(
    val code: Int,
    val data: VersionEntity,
    val msg: String
)

data class VersionEntity(
    val VId: String,
    val VName: String,
    val downloadURl: String,
    val isUpdate: Boolean,
    val solveTProblem: String
)