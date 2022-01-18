package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.InformacionDeIdetidadAuthIdCardRepository
import com.example.flashfeso_lwj.flashfeso.repository.MobiRecordRepository

class InformacionDeldetidadViewModel @ViewModelInject constructor(
    private val mobiRecordRepository: MobiRecordRepository,
    private val idCardRepository: InformacionDeIdetidadAuthIdCardRepository
): ViewModel() {

    val liveData = mobiRecordRepository.getDataLiveData()
    fun query(map: HashMap<String, Any>){
        mobiRecordRepository.query(map)
    }

    val idCardLiveData = mobiRecordRepository.getDataLiveData()
    fun queryIdCard(map: HashMap<String, Any>){
        idCardRepository.query(map)
    }
}