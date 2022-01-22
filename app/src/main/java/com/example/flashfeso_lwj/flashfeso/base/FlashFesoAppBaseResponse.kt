package com.example.flashfeso_lwj.flashfeso.base

import com.example.lwj_common.common.net.BaseResponse

abstract class FlashFesoAppBaseResponse<T>(
    code: Int ,
    data: T?,
    msg: String?,
    val dataId: Int,
    val totalCount: Int,
): BaseResponse<T>(code, data, msg)