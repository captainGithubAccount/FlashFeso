package com.example.flashfeso_lwj.flashfeso.repository

import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.flashfeso.api.data.service.InicioAuthUserInfoService
import com.example.flashfeso_lwj.flashfeso.entity.AuthUserInfoEntity
import com.example.lwj_common.common.repository.BaseRepository
import javax.inject.Inject

class InicioAuthUserInfoRepository4 @Inject constructor(
    private val service: InicioAuthUserInfoService
): BaseRepository<AuthUserInfoEntity>() {

    fun query() = whenLauchInIO {
        val dataResult = service.getAuthUserInfo().getDataResult()
        dataResult.whenSuccess {
            it?.run{_dataLiveData.postValue(DataResult.Success(it))}
        }
        dataResult.whenError {
            _dataLiveData.postValue(it)
        }
    }
}