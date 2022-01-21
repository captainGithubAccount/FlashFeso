package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.InicioAuthUserInfoService
import com.example.flashfeso_lwj.flashfeso.entity.AuthUserInfoEntity
import com.example.flashfeso_lwj.flashfeso.base.BaseRepository2
import javax.inject.Inject

class InicioAuthUserInfoRepository3 @Inject constructor(
    private val service: InicioAuthUserInfoService
): BaseRepository2<AuthUserInfoEntity>() {

    fun query() = onLauchInIO {
        val dataResult = service.getAuthUserInfo().getDataResult()
        dataResult.whenSuccess {
            it?.run{_dataLiveData.postValue(DataResult.Success(it))}
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
    }
}