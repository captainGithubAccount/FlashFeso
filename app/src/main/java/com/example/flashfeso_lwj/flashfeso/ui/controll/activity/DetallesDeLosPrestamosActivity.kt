package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BaseDbActivity
import com.example.flashfeso_lwj.databinding.ActivityDetallesDeLosPrestamosBinding
import com.example.flashfeso_lwj.flashfeso.entity.CurrDetailEntity
import com.example.lwj_base.common.base.BaseConstants
import com.example.lwj_common.common.utils.DoubleUtils
import com.example.lwj_common.common.utils.NumberUtils
import dagger.hilt.android.AndroidEntryPoint

/*
* 申请借款界面
* */

@AndroidEntryPoint
class DetallesDeLosPrestamosActivity : BaseDbActivity<ActivityDetallesDeLosPrestamosBinding>(){
    var mCurrDetailEntity: CurrDetailEntity? = null

    var mIsAgain: Boolean = false

    var mIsAuthentication: Boolean = false
    private var minAmount = "0"
    private var maxAmount = "0"
    private val minAmountText: TextView? = null


    private val isAgree = true
    private val viewError: LinearLayout? = null
    private val isAgain = false
    private val isAuthentication = false
    private val montoDelPrestamosTv: TextView? = null
    private val subImg: ImageView? = null
    private val addImg: ImageView? = null
    private var loadAmount = "0"
    private val btnCambiar: TextView? = null
    private val longitude = 0.0
    private val latitude = 0.0


    override fun observe() {

    }

    override fun beforeCreateView() {
        super.beforeCreateView()
        mCurrDetailEntity = intent.getParcelableExtra("currDetailsBean")!!
        if(BaseConstants.ISLOG) Log.d("DetallesDeLosPrestamos", mCurrDetailEntity.toString())

        mIsAuthentication = intent.getBooleanExtra("currDetailsBean", false)

        mIsAgain = intent.getBooleanExtra("isAgain", false)
    }

    override fun ActivityDetallesDeLosPrestamosBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.detalles_de_los_prestamos)
        binding.progress.llProgress.visibility = View.GONE
        binding.empty.viewEmpty.visibility = View.GONE
        initViewWhenOtherSituations()
    }

    private fun initViewWhenOtherSituations() {
        if(mCurrDetailEntity != null){
            binding.progress.llProgress.visibility = View.GONE
            binding.empty.viewEmpty.visibility = View.GONE
            binding.error.llError.visibility = View.GONE

            val money = resources.getString(R.string.money_symbol)
            val dias = resources.getString(R.string.días)


            //最小申请金额
            minAmount = DoubleUtils.divTOString(mCurrDetailEntity?.minAmount, "100", 2)
            minAmountText?.setText(money + NumberUtils.goToZeroString(minAmount))

            //最大申请金额
            maxAmount = DoubleUtils.divTOString(mCurrDetailEntity?.loanAmount, "100", 2)
            loadAmount = maxAmount
            binding.loanAmountText.setText(money + NumberUtils.goToZeroString(loadAmount))
            //申请金额
            montoDelPrestamosTv?.setText(money + NumberUtils.goToZeroString(maxAmount))
            //放款金额
            binding.disburalAmount.setText(money + NumberUtils.goToZeroString(DoubleUtils.divTOString(
                mCurrDetailEntity?.disburalAmount,
                "100",
                2)))
            //借贷期限
            binding.tenure.setText(mCurrDetailEntity?.tenure.toString() + " " + dias)
            //银行卡号
            binding.bbvaBanvomerTv.setText(mCurrDetailEntity?.bankNo)
            //应付金额
            binding.montoTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divTOString(
                mCurrDetailEntity?.repaymentAmount,
                "100",
                2)))
            //付款截止日期
            binding.fechaTv.setText(mCurrDetailEntity?.repayDate)
            //管理费
            binding.comisionTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divTOString(
                mCurrDetailEntity?.processingFee,
                "100",
                2)))
            //利息
            binding.interesTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divTOString(
                mCurrDetailEntity?.interest,
                "100",
                2)))

            addImg?.setImageDrawable(resources.getDrawable(R.drawable.icon_add_grey))

            if (maxAmount.toDouble() <= minAmount.toDouble()) {
                subImg?.setImageDrawable(resources.getDrawable(R.drawable.icon_sub_grey))
            } else {
                subImg?.setImageDrawable(resources.getDrawable(R.drawable.icon_sub_black))
            }
        }else if(isAgain){

        }


    }


}