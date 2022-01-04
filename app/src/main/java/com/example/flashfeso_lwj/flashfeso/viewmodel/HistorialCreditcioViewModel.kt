package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.HistorialCreditcioRepository
import javax.inject.Inject

class HistorialCreditcioViewModel @ViewModelInject constructor(
    private val historialCreditcioRepository: HistorialCreditcioRepository
): ViewModel() {
    val liveData = historialCreditcioRepository.getDataLiveData()
    fun query(map: HashMap<String, Any>) {
        historialCreditcioRepository.query(map)
    }
}