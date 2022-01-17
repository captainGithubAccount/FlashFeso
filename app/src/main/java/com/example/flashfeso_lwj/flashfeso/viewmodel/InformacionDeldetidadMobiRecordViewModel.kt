package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.MobiRecordRepository

class InformacionDeldetidadMobiRecordViewModel @ViewModelInject constructor(
    private val mobiRecordRepository: MobiRecordRepository
): ViewModel() {

    val liveData = mobiRecordRepository.getDataLiveData()
    fun query(map: HashMap<String, Any>){
        mobiRecordRepository.query(map)
    }
}