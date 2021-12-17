package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flashfeso_lwj.databinding.ActivityMainBinding
import com.example.flashfeso_lwj.flashfeso.base.BaseDbActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseDbActivity<ActivityMainBinding>(){
    override fun ActivityMainBinding.initView() {

        binding.tvTest.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun observe() {

    }

}
