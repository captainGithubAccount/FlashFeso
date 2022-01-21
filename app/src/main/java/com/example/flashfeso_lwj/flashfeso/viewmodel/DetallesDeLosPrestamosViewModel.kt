package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.AmountChooseRepository
import com.example.flashfeso_lwj.flashfeso.repository.GenerateOrderRepository

class DetallesDeLosPrestamosViewModel @ViewModelInject constructor(
    private val generateOrderRepository: GenerateOrderRepository,
    private val amountChooseRepository: AmountChooseRepository
): ViewModel() {
    val generateOrderLiveData = generateOrderRepository.getDataLiveData()
    fun queryGenerateOrder(map: HashMap<String, Any>) {
        generateOrderRepository.query(map)
    }

    val amountChooseLiveData = amountChooseRepository.getDataLiveData()
    fun queryAmountChoose(map: HashMap<String, Any>) {
        amountChooseRepository.query(map)
    }
}