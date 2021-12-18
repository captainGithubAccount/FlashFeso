package com.example.flashfeso_lwj.flashfeso.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


import androidx.viewbinding.ViewBinding
import com.example.flashfeso_lwj.GetBinding
import com.example.flashfeso_lwj.flashfeso.utils.Constants


abstract class BaseDbActivity<T: ViewBinding>: AppCompatActivity(), GetBinding<T> {

    private var mFirstClickTime = 0L
    private var mSecondClickTime = 0L
    private  var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onStart() {
        super.onStart()
        afterInitView()
    }

    //两次点击的时间
    protected fun isClickUseful(): Boolean{
        mFirstClickTime = mSecondClickTime
        mSecondClickTime = System.currentTimeMillis()
        return mSecondClickTime - mFirstClickTime > Constants.DOUBLE_CLICK_TIME
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeCreateView()
        observe()
        _binding = getBindingByReflex(layoutInflater)
        setContentView(binding.root)
        binding.initView()
    }

    abstract fun observe()

    abstract fun T.initView()

    protected open fun afterInitView() {}

    protected open fun beforeCreateView() {}
}
