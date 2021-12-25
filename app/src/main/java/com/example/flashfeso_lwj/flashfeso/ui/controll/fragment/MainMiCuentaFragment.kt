package com.example.flashfeso_lwj.flashfeso.ui.controll.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.common.ui.controll.fragment.BaseDbFragment
import com.example.flashfeso_lwj.databinding.FragmentMainMicuentaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMiCuentaFragment : BaseDbFragment<FragmentMainMicuentaBinding>(){

    companion object{
        //注意该方法获取的不是单例, 该项目中只调用一次该方法, 所以效果也是只有一个对象创建
        fun getInstance(): MainMiCuentaFragment = MainMiCuentaFragment()
    }

    override fun observe() {

    }

    override fun FragmentMainMicuentaBinding.initView() {

    }

}