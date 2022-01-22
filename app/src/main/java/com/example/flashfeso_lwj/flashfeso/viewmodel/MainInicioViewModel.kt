package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.*
import javax.inject.Inject

class MainInicioViewModel @ViewModelInject constructor(
    private val inicioCurrDetailRepository: InicioCurrDetailRepository,
    private val inicioCurrDetailRepository2: InicioCurrDetailRepository2,
    private val inicioAuthUserInfoRepository: InicioAuthUserInfoRepository,
    private val inicioAuthUserInfoRepository2: InicioAuthUserInfoRepository2,
    private val inicioAuthUserInfoRepository3: InicioAuthUserInfoRepository3,
    private val inicioAuthUserInfoRepository4: InicioAuthUserInfoRepository4
): ViewModel() {
    val currDetailWhenLoginLiveData = inicioCurrDetailRepository.getDataLiveData()
    fun queryCurrDetailWhenLogin(map: HashMap<String, Any>){
        inicioCurrDetailRepository.query(map)
    }



    val currDetailWhenLoginAndNotifyLiveData = inicioCurrDetailRepository2.getDataLiveData()
    fun queryCurrDetailWhenLoginAndNotify(map: HashMap<String, Any>){
        inicioCurrDetailRepository2.query(map)
    }



    fun queryWhenNotLoginAndNotify(){
        inicioAuthUserInfoRepository.query()
    }
    val authUserInfoWhenNotLoginAndNotifyLiveData = inicioAuthUserInfoRepository.getDataLiveData()



    /**
     * 这里命名有问题, 应该改为查询默认数据如, queryDefaultData
     * */
    fun queryAuthUserInfoWhenLogin(){
        inicioAuthUserInfoRepository2.query()
    }
    val authUserInfoWhenLoginLiveData = inicioAuthUserInfoRepository2.getDataLiveData()




    /**
     * 这里命名有问题, 应该改为查询默认数据如, queryDefaultData
     * */
    fun queryAuthUserInfoWhenNotLogin(){
        inicioAuthUserInfoRepository3.query()
    }
    val authUserInfoWhenNotLoginLiveData = inicioAuthUserInfoRepository3.getDataLiveData()




    fun queryAuthUserInfoWhenLoginAndNotify(){
        inicioAuthUserInfoRepository4.query()
    }
    val authUserInfoWhenLoginAndNotifyLiveData = inicioAuthUserInfoRepository4.getDataLiveData()
}