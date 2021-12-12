package com.example.flashfeso_lwj.flashfeso.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


import androidx.viewbinding.ViewBinding
import com.example.flashfeso_lwj.GetBinding


abstract class BaseDbActivity<T: ViewBinding>: AppCompatActivity(), GetBinding<T> {
    private  var _binding: T? = null
    protected val binding get() = _binding

    override fun onStart() {
        super.onStart()
        afterInitView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeInitView()
        _binding = getBindingByReflex(layoutInflater)
        setContentView(binding?.root)
        binding?.initView()
    }

    abstract fun T.initView()

    protected open fun afterInitView() {}

    protected open fun beforeInitView() {}




}
