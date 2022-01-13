package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityInformacionDeIdetidadBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.RvAdapterTakePhotoSelect
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.CommonSelectDialog
import com.example.flashfeso_lwj.flashfeso.utils.back

class InformacionDeIdetidadActivity: BasePageStyleActivity<ActivityInformacionDeIdetidadBinding>(),InfomationSelectItemOnClickListener {

    lateinit var mFirstCommonSelectDialog: CommonSelectDialog
    override fun observe() {

    }

    override fun ActivityInformacionDeIdetidadBinding.initView() {

    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.franteImg.setOnClickListener {
            if(isClickUseful()){
                hideKeyboardAll()
                调接口打点
                mFirstCommonSelectDialog = CommonSelectDialog()
                val data = resources.getStringArray(R.array.array_deldetidad_take_photo).toList()
                mFirstCommonSelectDialog.setAdapter(RvAdapterTakePhotoSelect(data, this, 0))
                mFirstCommonSelectDialog.show(supportFragmentManager, "DeIdetidad_mFirstCommonSelectDialog")
            }
        }
    }

    private fun hideKeyboardAll() {
        if(binding.curpTv.hasFocus()){
            val imm = binding.curpTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.curpTv.windowToken, 0)
        }
        if(binding.apellidoPaternoTv.hasFocus()){
            val imm = binding.apellidoPaternoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.apellidoPaternoTv.windowToken, 0)
        }
        if(binding.apellidoMaternoTv.hasFocus()){
            val imm = binding.apellidoMaternoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.apellidoMaternoTv.windowToken, 0)
        }
        if(binding.nombreCompletoTv.hasFocus()){
            val imm = binding.nombreCompletoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.nombreCompletoTv.windowToken, 0)
        }
        if(binding.rfcTv.hasFocus()){
            val imm = binding.rfcTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.rfcTv.windowToken, 0)
        }
    }

    override fun onDialogItemClick(list: List<String>, flag: Int) {
        when(flag){
            0 -> {
                val position = list[1].toInt()
                if(position == 0){
                    startOpenCamera(FRENTA_DE_INE)
                }else if(position == 1){
                    startSelectImage(SELECR_IMAGE_FRENTA_DE_INE)
                }
                mFirstCommonSelectDialog.dismiss()
            }
        }
    }

}