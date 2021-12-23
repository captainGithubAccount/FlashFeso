package com.example.flashfeso_lwj.common.ui.controll.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope


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

    }

    protected open suspend fun observeWhenCreatedWithLifecycle(){}

    protected open suspend fun observeWhenStartedWithLifecycle(){}

    protected open suspend fun observeWhenResumedWithLifecycle(){}


    init{
        /*lifecycleScope.launchWhenCreated {
            fun observeWhenCreatedWithOpenScope(block: () -> Unit) = launch {
                block.invoke()
            }
        }*/

        lifecycleScope.launchWhenCreated {
            observeWhenCreatedWithLifecycle()
        }

        lifecycleScope.launchWhenStarted {
            observeWhenStartedWithLifecycle()
        }

        lifecycleScope.launchWhenResumed {
            observeWhenResumedWithLifecycle()
        }

        /*lifecycleScope.launchWhenCreated {
            supervisorScope {
            //出现异常不关闭其他子协程, 注意在作用域里面抛异常, 子协程也会停止运行, 所以要在作用域里面开辟一个
            //新的协程, 如lanch启动的协程, 这样当该子协程出异常不会关闭其他子协程
                observeWhenCreatedWithLifecycle()
            }
        }*/
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

        _binding = getAtvOrFrgmBindingByReflex(layoutInflater)
        afterBindView()
        setContentView(binding.root)
        binding.initView()
        afterInitView(savedInstanceState)
    }


    private fun afterInitView(savedInstanceState: Bundle?) {}

    protected abstract fun observe()

    abstract fun T.initView()

    protected open fun afterBindView() {}

    protected open fun beforeCreateView() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
