package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityDetallesDelPrestamoBinding
import com.example.flashfeso_lwj.flashfeso.entity.OrderHistoryDataEntity
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.utils.DoubleUtils
import com.example.lwj_common.common.ui.controll.tools.utils.NumberUtils
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * 贷款详情
 * */
@AndroidEntryPoint
class DetallesDelPrestamoActivity: BasePageStyleActivity<ActivityDetallesDelPrestamoBinding>() {
    private var data: OrderHistoryDataEntity? = null

    companion object {
        val TAG_DATA_BEAN = "tag_data_bean"
    }

    override fun observe() {

    }

    override fun ActivityDetallesDelPrestamoBinding.initView() {
        header.tvCommonBarTitle.text = resources.getString(R.string.detalles_del_prestamo)
        if(data != null) {
            showSuccess()
            if(data?.orderStatus.isUseful()) {
                when(data?.orderStatus) {
                    "1" -> {
                        orderStatusTv.text = resources.getString(R.string.el_prestamo_fallo)
                        orderStatusTv.setTextColor(resources.getColor(R.color.color_D81E06))
                    }
                    "4" -> {
                        orderStatusTv.text = resources.getString(R.string.reembolso_exitoso)
                        orderStatusTv.setTextColor(resources.getColor(R.color.color_00DE00))
                    }
                    "5" -> {
                        orderStatusTv.text = resources.getString(R.string.reembolso_atrasado)
                        orderStatusTv.setTextColor(resources.getColor(R.color.color_02A7F0))
                    }
                    else -> {
                        orderStatusTv.text = resources.getString(R.string.sin_resolver)
                        orderStatusTv.setTextColor(resources.getColor(R.color.color_02A7F0))
                    }
                }
            } else {
                orderStatusTv.text = ""
            }

            if(data?.applyNumber.isUseful()) {
                applyNumberTv.text = data?.applyNumber;
            } else {
                applyNumberTv.text = "";
            }

            if(data?.repayDate.isUseful()) {
                repayDateTv.text = data?.repayDate
            } else {
                repayDateTv.text = "";
            }


            //申请金额
            if(data?.loanMoney.isUseful()) {
                loanMoneyTv.setText(getResources().getString(R.string.money_symbol).plus(NumberUtils.goToZeroString(DoubleUtils.divToString(data?.loanMoney, "100", 2))))
            } else {
                loanMoneyTv.text = ""
            }


            //申请期限
            if(data?.loanTerm.isUseful()) {
                loanTermTv.setText(data?.loanTerm.toString().plus(" ").plus(getResources().getString(R.string.días)))
            } else {
                loanTermTv.text = ""
            }


            //服务费
            if(data?.serviceMoney.isUseful()) {
                serviceMoneyTv.setText(getResources().getString(R.string.money_symbol).plus(NumberUtils.goToZeroString(DoubleUtils.divToString(data?.serviceMoney, "100", 2))))
            } else {
                serviceMoneyTv.text = ""
            }


            //利息
            if(data?.interest.isUseful()) {
                interestTv.setText(resources.getString(R.string.money_symbol).plus(NumberUtils.goToZeroString(DoubleUtils.divToString(data?.interest, "100", 2))))
            } else {
                interestTv.text = ""
            }

            if(data?.orderStatus.isUseful() && data?.overdueMoney?.let {StringUtils.isNumber(it)} == true) {
                overdueDaysLl.visibility = View.VISIBLE
                overdueMoneyLl.visibility = View.VISIBLE
                overdueTitle.visibility = View.VISIBLE
                overdueLine.visibility = View.VISIBLE

                //逾期天数 逾期有
                overdueDaysTv.setText(data?.overdueMoney.toString().plus(" ").plus(resources.getString(R.string.días)))

                //逾期金额 逾期有
                overdueMoneyTv.setText(resources.getString(R.string.money_symbol).plus(NumberUtils.goToZeroString(DoubleUtils.divToString(data?.overdueMoney, "100", 2))))
            } else {
                overdueDaysLl.visibility = View.GONE
                overdueMoneyLl.visibility = View.GONE
                overdueTitle.visibility = View.GONE
                overdueLine.visibility = View.GONE
            }

        } else {
            empty.viewEmpty.visibility = View.VISIBLE
            progress.llProgress.visibility = View.GONE
        }

    }

    private fun showSuccess() {
        binding.empty.viewEmpty.visibility = View.GONE
        binding.progress.llProgress.visibility = View.GONE
    }

    override fun beforeCreateView() {
        super.beforeCreateView()
        data = intent.getParcelableExtra(DetallesDelPrestamoActivity.TAG_DATA_BEAN)
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

    }

}