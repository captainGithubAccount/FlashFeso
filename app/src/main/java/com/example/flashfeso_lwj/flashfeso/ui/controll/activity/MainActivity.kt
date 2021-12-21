package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Intent
import com.example.flashfeso_lwj.databinding.ActivityMainBinding
import com.example.flashfeso_lwj.common.ui.controll.activity.BasePageStyleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BasePageStyleActivity<ActivityMainBinding>(){


    override fun ActivityMainBinding.initView() {

        binding.tvTest.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }


    override fun observe() {

    }

}
