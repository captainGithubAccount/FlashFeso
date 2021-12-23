package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import com.example.flashfeso_lwj.databinding.ActivityMainBinding
import com.example.flashfeso_lwj.common.ui.controll.activity.BasePageStyleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BasePageStyleActivity<ActivityMainBinding>(){


    override fun ActivityMainBinding.initView() {

    }

    override fun afterBindView() {
        super.afterBindView()
        setSize(binding.rbMainBottomInfo)
        setSize(binding.rbMainBottomMe)
    }


    override fun observe() {

    }

}
