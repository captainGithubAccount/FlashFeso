package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BaseDbActivity
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityInformacionBasicaBinding

class InformacionBasicaActivity :BasePageStyleActivity<ActivityInformacionBasicaBinding>(){
    override fun observe() {

    }

    override fun ActivityInformacionBasicaBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.Informacion_basica)
    }

}