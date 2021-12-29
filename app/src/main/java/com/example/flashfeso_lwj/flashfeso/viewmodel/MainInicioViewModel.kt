package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.InicioAuthUserInfoRepository
import com.example.flashfeso_lwj.flashfeso.repository.InicioCurrDetailRepository
import javax.inject.Inject

class MainInicioViewModel @ViewModelInject constructor(
    private val inicioCurrDetailRepository: InicioCurrDetailRepository,
    private val inicioAuthUserInfoRepository: InicioAuthUserInfoRepository
): ViewModel() {
    val inicioCurrDetailLiveData = inicioCurrDetailRepository.getDataLiveData()
    fun queryInicioCurrDetail(map: HashMap<String, Any>){
        inicioCurrDetailRepository.query(map)
    }

    val inicioCurrDetailHideLiveData = inicioCurrDetailRepository.getDataLiveData()
    fun queryInicioCurrDetailHide(map: HashMap<String, Any>){
        inicioCurrDetailRepository.query(map)
    }

    fun queryInicioAuthUserInfoSingleHide(){
        inicioAuthUserInfoRepository.query()
    }
    val inicioAuthUserInfoSingleHideLiveData = inicioAuthUserInfoRepository.getDataLiveData()

    fun queryInicioAuthUserInfo(){
        inicioAuthUserInfoRepository.query()
    }
    val inicioAuthUserInfoLiveData = inicioAuthUserInfoRepository.getDataLiveData()


    fun queryInicioAuthUserInfoSingle(){
        inicioAuthUserInfoRepository.query()
    }
    val inicioAuthUserInfoSingleLiveData = inicioAuthUserInfoRepository.getDataLiveData()

    fun queryInicioAuthUserInfoHide(){
        inicioAuthUserInfoRepository.query()
    }
    val inicioAuthUserInfoHideLiveData = inicioAuthUserInfoRepository.getDataLiveData()
}