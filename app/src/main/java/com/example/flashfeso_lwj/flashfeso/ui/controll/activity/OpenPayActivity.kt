package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityOpenPayBinding
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.VpListFragmentAdapter
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.VpListFragmentAdapter.ListFragmentEntity
import com.example.flashfeso_lwj.flashfeso.ui.controll.fragment.MainInicioFragment.Companion.getInstance
import com.example.flashfeso_lwj.flashfeso.ui.controll.fragment.OpenPayOfflineFragment
import com.example.flashfeso_lwj.flashfeso.ui.controll.fragment.OpenPayOnlineFragment
import com.example.lwj_common.common.ui.controll.tools.utils.DensityUtil
import com.google.android.material.snackbar.Snackbar

class OpenPayActivity: BasePageStyleActivity<ActivityOpenPayBinding>() {
    companion object{

        val TAG_OPENPAY = "tag_openpay"
    }
    var loanId: String? = null
    val TAB_WIDTH: Float = DensityUtil.getDisplayWidthDp(this) / 2
    override fun observe() {

    }

    override fun ActivityOpenPayBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.quiero_pagar)


    }

    override fun beforeCreateView() {
        super.beforeCreateView()
        loanId = intent.getStringExtra(TAG_OPENPAY)

        val list: ArrayList<ListFragmentEntity> = arrayListOf()
        list.add(ListFragmentEntity(OpenPayOnlineFragment(loanId!!), resources.getString(R.string.pago_online)))
        list.add(ListFragmentEntity(OpenPayOfflineFragment(loanId!!), resources.getString(R.string.pago_offline)))
        binding.openPayVp.adapter = VpListFragmentAdapter(supportFragmentManager, list)

        binding.stlNav.tabWidth = TAB_WIDTH
        binding.stlNav.setViewPager(binding.openPayVp)

    }

}