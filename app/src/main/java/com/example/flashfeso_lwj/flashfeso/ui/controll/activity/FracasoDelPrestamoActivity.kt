package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityFracasoDelPrestamoBinding
import com.example.flashfeso_lwj.flashfeso.entity.CurrDetailEntity
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.utils.DoubleUtils
import com.example.lwj_common.common.ui.controll.tools.utils.NumberUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FracasoDelPrestamoActivity: BasePageStyleActivity<ActivityFracasoDelPrestamoBinding>() {
    var data: CurrDetailEntity? = null
    override fun observe() {

    }

    override fun ActivityFracasoDelPrestamoBinding.initView() {
        showProgress()
        if(data != null) {
            showSuccess()

            loadAmount.text = "$".plus(NumberUtils.goToZeroString(DoubleUtils.div(data?.loanAmount, "100", 2)))
            if(data?.rejectedReDate.isUseful()){
                loadDate.text = data?.rejectedReDate
            }
        } else {

            showEmpty()
        }
    }

    private fun showEmpty() {
        binding.empty.viewEmpty.visibility = View.VISIBLE
        binding.progress.llProgress.visibility = View.GONE
    }

    fun showSuccess() {
        binding.empty.viewEmpty.visibility = View.GONE
        binding.progress.llProgress.visibility = View.GONE
    }

    fun showProgress() {
        binding.empty.viewEmpty.visibility = View.GONE
        binding.progress.llProgress.visibility = View.VISIBLE
    }

    override fun beforeCreateView() {
        super.beforeCreateView()
        data = intent.getParcelableExtra("currDetailsBean")
    }


}