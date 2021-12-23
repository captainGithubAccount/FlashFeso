package com.example.flashfeso_lwj.common.ui.controll.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.flashfeso_lwj.App
import com.example.flashfeso_lwj.GetBinding
import com.example.flashfeso_lwj.flashfeso.utils.Constants

abstract class BaseDbFragment<T: ViewBinding>: Fragment(), GetBinding<T> {
    private  var _activity: Activity? = null

    private var mFirstClickTime = 0L
    private var mSecondClickTime = 0L
    private  var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _activity = getActivity()
    }

    protected fun getAtvContext(): Context = _activity?: App.instance!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeCreateView()
        observe()
        _binding = getAtvOrFrgmBindingByReflex(layoutInflater)
        afterBindView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterInitView(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
    }

    //两次点击的时间
    protected fun isClickUseful(): Boolean{
        mFirstClickTime = mSecondClickTime
        mSecondClickTime = System.currentTimeMillis()
        return mSecondClickTime - mFirstClickTime > Constants.DOUBLE_CLICK_TIME
    }

    protected abstract fun observe()

    //已有界面, 对界面内容做初始化工作
    abstract fun T.initView()

    //没有显示出界面但是已有bindnig(即已存界面的引用),可以设置监听
    protected open fun afterBindView() {}

    //加载数据(通常用于需要数据才能显示界面)
    protected open fun beforeCreateView() {}

    //拿到view之后创建适配器等
    protected fun afterInitView( savedInstanceState: Bundle?){}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}