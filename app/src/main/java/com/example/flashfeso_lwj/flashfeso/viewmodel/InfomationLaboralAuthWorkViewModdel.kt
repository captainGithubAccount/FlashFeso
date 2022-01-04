package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.InfomationLaboralAuthWorkRepository

class InfomationLaboralAuthWorkViewModdel @ViewModelInject constructor(
    private val infomationLaboralAuthWorkRepository: InfomationLaboralAuthWorkRepository
): ViewModel() {

    fun query(map: HashMap<String, Any>){
        infomationLaboralAuthWorkRepository.query(map)
    }
    val livedata = infomationLaboralAuthWorkRepository.getDataLiveData()
}