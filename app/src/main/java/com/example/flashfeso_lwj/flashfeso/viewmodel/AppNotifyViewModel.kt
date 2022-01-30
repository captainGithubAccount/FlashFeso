package com.example.flashfeso_lwj.flashfeso.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppNotifyViewModel @ViewModelInject constructor(): ViewModel() {
    private var _notifyLiveData: MutableLiveData<Int>? = null
    val notifyLiveData: LiveData<Int> get() = _notifyLiveData!!

    fun query() {//UnLoginBean
        _notifyLiveData?.postValue(1)
    }

}