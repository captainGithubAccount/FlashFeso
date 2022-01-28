package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.flashfeso_lwj.flashfeso.repository.OrderHistoryRepsitory

class MiPrestamoViewModel @ViewModelInject constructor(
    private val orderHistoryRepsitory: OrderHistoryRepsitory
): ViewModel() {

    fun query(){
        orderHistoryRepsitory.query()
    }

    val liveData = orderHistoryRepsitory.getLiveData()
}