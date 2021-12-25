package com.example.flashfeso_lwj.flashfeso.ui.controll.fragment

import com.example.flashfeso_lwj.base.ui.controll.fragment.BaseDbFragment
import com.example.flashfeso_lwj.databinding.FragmentMainInicioBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainInicioFragment : BaseDbFragment<FragmentMainInicioBinding>(){

    companion object{
        //注意该方法获取的不是单例, 该项目中只调用一次该方法, 所以效果也是只有一个对象创建
        fun getInstance(): MainInicioFragment = MainInicioFragment()
    }

    override fun observe() {

    }

    override fun FragmentMainInicioBinding.initView() {

    }

}