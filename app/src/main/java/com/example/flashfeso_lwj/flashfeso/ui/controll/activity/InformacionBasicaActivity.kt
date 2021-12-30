package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityInformacionBasicaBinding
import com.example.flashfeso_lwj.flashfeso.utils.addToast
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.utils.textIsNotEmpty
import com.example.lwj_common.common.utils.StringUtils

class InformacionBasicaActivity :BasePageStyleActivity<ActivityInformacionBasicaBinding>(){
    override fun observe() {

    }

    override fun ActivityInformacionBasicaBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.Informacion_basica)
    }




    override fun afterBindView() {
        super.afterBindView()

        binding.confirm.setOnClickListener {
            if(binding.youBian.textIsNotEmpty()){
                addToast(this,resources.getString(R.string.introduzca_el_codigo_postal))
                Log.d("---校验", "error")
                return@setOnClickListener
            }
        }


        binding.header.ivCommonBarBack.back(this)
    }

}