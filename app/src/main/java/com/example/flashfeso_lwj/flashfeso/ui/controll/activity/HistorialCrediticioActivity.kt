package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.entity.DataResult
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityHistorialCrediticioBinding
import com.example.flashfeso_lwj.flashfeso.event.InfomationSelectItemOnClickListener
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.InfomationSelectDialog
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.utils.textIsEmpty
import com.example.flashfeso_lwj.flashfeso.viewmodel.HistorialCreditcioViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
* 认证三
* */
@AndroidEntryPoint
class HistorialCrediticioActivity : BasePageStyleActivity<ActivityHistorialCrediticioBinding>(), InfomationSelectItemOnClickListener {
    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null
    val mViewModel: HistorialCreditcioViewModel by viewModels()
    val mLoginViewModel: LoginViewModel by viewModels()
    private var isAbandonmentOfRepayment = true
    lateinit var mQuestionTwoDialog: InfomationSelectDialog
    lateinit var mQuestionThreeDialog: InfomationSelectDialog
    lateinit var mQuestionFourDialog: InfomationSelectDialog
    lateinit var mQuestionFiveDialog: InfomationSelectDialog
    lateinit var mQuestionSixDialog: InfomationSelectDialog
    lateinit var mQuestionSevenDialog: InfomationSelectDialog
    var mQuestionTwoPosition = 0
    var mQuestionThreePosition = 0
    var mQuestionFourPosition = 0
    var mQuestionFivePosition = 0
    var mQuestionSixPosition = 0
    var mQuestionSevenPosition = 0
    override fun observe() {
        mViewModel.liveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenSuccessResponse {
                if ((it as DataResult.Success).successMessagle?.equals(getResources().getString(R.string.success))!!) {
                    Toast.makeText(this@HistorialCrediticioActivity, it.successMessagle, Toast.LENGTH_SHORT).show()
                    onBackPressed()
                    mLoginViewModel.queryNotifyInicioBeanLiveData()
                    startActivity(InformacionDeContactosActivity::class.java)
                }
            }
            it.whenError {

                Toast.makeText(this@HistorialCrediticioActivity, (it as DataResult.Error).errorMessage, Toast.LENGTH_SHORT).show()
            }
            it.whenClear {
                InfoUtil.clear()
                mLoginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this@HistorialCrediticioActivity, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleProgressDialogUtil = null
    }

    override fun ActivityHistorialCrediticioBinding.initView() {

    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.questionOneSi.setOnClickListener {
            isAbandonmentOfRepayment = true
            binding.questionOneSi.setBackground(resources.getDrawable(R.drawable.bg_confirm))
            binding.questionOneSi.setTextColor(resources.getColor(R.color.color_FFFFFF))
            binding.questionOneNo.setBackground(resources.getDrawable(R.drawable.bg_confirm_stroke))
            binding.questionOneNo.setTextColor(resources.getColor(R.color.color_main_background))
        }

        binding.questionOneNo.setOnClickListener {
            isAbandonmentOfRepayment = false
            binding.questionOneSi.setBackground(resources.getDrawable(R.drawable.bg_confirm_stroke))
            binding.questionOneSi.setBackground(resources.getDrawable(R.drawable.bg_confirm_stroke))
            binding.questionOneSi.setTextColor(resources.getColor(R.color.color_main_background))
            binding.questionOneNo.setBackground(resources.getDrawable(R.drawable.bg_confirm))
            binding.questionOneNo.setTextColor(resources.getColor(R.color.color_FFFFFF))
        }

        binding.questionTwoLl.setOnClickListener {
            if (isClickUseful()) {
                val data = resources.getStringArray(R.array.array_overdue_days_all).toList()
                mQuestionTwoDialog = InfomationSelectDialog.newInstance().addSetting(2, data, this)
                mQuestionTwoDialog.show(supportFragmentManager, "mQuestionTwoDialog")
            }
        }

        binding.questionThreeLl.setOnClickListener {
            if (isClickUseful()) {
                val data = resources.getStringArray(R.array.array_Loan_amount_all).toList()
                mQuestionThreeDialog = InfomationSelectDialog.newInstance().addSetting(3, data, this)
                mQuestionThreeDialog.show(supportFragmentManager, "mQuestionThreeDialog")
            }
        }

        binding.questionFourLl.setOnClickListener {
            if (isClickUseful()) {
                val data = resources.getStringArray(R.array.array_Loan_counts_all).toList()
                mQuestionFourDialog = InfomationSelectDialog.newInstance().addSetting(4, data, this)
                mQuestionFourDialog.show(supportFragmentManager, "mQuestionFourDialog")
            }
        }

        binding.questionFiveLl.setOnClickListener {
            if (isClickUseful()) {
                val data = resources.getStringArray(R.array.array_Loan_counts_all).toList()
                mQuestionFiveDialog = InfomationSelectDialog.newInstance().addSetting(5, data, this)
                mQuestionFiveDialog.show(supportFragmentManager, "mQuestioniFiveDialog")
            }
        }

        binding.questionSixLl.setOnClickListener {
            if (isClickUseful()) {
                val data = resources.getStringArray(R.array.array_Loan_counts_all).toList()
                mQuestionSixDialog = InfomationSelectDialog.newInstance().addSetting(6, data, this)
                mQuestionSixDialog.show(supportFragmentManager, "mQuestionSixDialog")
            }
        }

        binding.questionSevenLl.setOnClickListener {
            if (isClickUseful()) {
                val data = resources.getStringArray(R.array.array_Loan_counts_all).toList()
                mQuestionSevenDialog = InfomationSelectDialog.newInstance().addSetting(7, data, this)
                mQuestionSevenDialog.show(supportFragmentManager, "mQuestionSevenDialog")
            }
        }


        binding.confirm.setOnClickListener { v ->
            val questionTwo = binding.questionTwoTv.text.toString().trim()
            if (binding.questionTwoTv.textIsEmpty()) {
                Toast.makeText(this@HistorialCrediticioActivity, resources.getString(R.string.seleccione_el_numero_maximo_de_dias_atrasados), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val questionThree: String = binding.questionThreeTv.text.toString().trim()
            if (StringUtils.isEmpty(questionThree)) {
                Toast.makeText(this@HistorialCrediticioActivity, resources.getString(R.string.seleccione_el_importe_total_del_prestamo), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val questionFour: String = binding.questionFourTv.getText().toString().trim()
            if (StringUtils.isEmpty(questionFour)) {
                Toast.makeText(this@HistorialCrediticioActivity, resources.getString(R.string.seleccione_el_numero_de_prestamos), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val questionFive: String = binding.questionFiveTv.getText().toString().trim()
            if (StringUtils.isEmpty(questionFive)) {
                Toast.makeText(this@HistorialCrediticioActivity, resources.getString(R.string.seleccione_el_importe_de_la_subvencion), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val questionSix: String = binding.questionSixTv.getText().toString().trim()
            if (StringUtils.isEmpty(questionSix)) {
                Toast.makeText(this@HistorialCrediticioActivity, resources.getString(R.string.elija_el_numero_de_reembolso_oportuno), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val questionSeven: String = binding.questionSevenTv.getText().toString().trim()
            if (StringUtils.isEmpty(questionSeven)) {
                Toast.makeText(this@HistorialCrediticioActivity, resources.getString(R.string.seleccione_el_numero_de_pagos_atrasados), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var isStopRepay = 1
            isStopRepay = if (isAbandonmentOfRepayment) 1 else 0

            if (isClickUseful()) {
                queryAuthLoanHistory(isStopRepay, mQuestionTwoPosition, mQuestionThreePosition, mQuestionFourPosition, mQuestionFivePosition, mQuestionSixPosition, mQuestionSevenPosition)
            }


        }
    }

    private fun queryAuthLoanHistory(isStopRepay: Int, daysInArrears: Int, totalArrears: Int, borrowCount: Int, inRepayAppCount: Int, onTimeRepayAppCount: Int, overdueAppCount: Int) {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val map: MutableMap<String, Any> = HashMap()
        map["isStopRepay"] = isStopRepay
        map["daysInArrears"] = daysInArrears
        map["totalArrears"] = totalArrears
        map["borrowCount"] = borrowCount
        map["inRepayAppCount"] = inRepayAppCount
        map["onTimeRepayAppCount"] = onTimeRepayAppCount
        map["overdueAppCount"] = overdueAppCount
        mViewModel.query(map as HashMap<String, Any>)
    }

    override fun onDialogItemClick(list: List<String>, flag: Int) {
        when (flag) {
            Question.TWO.value -> {
                mQuestionTwoDialog.dismiss()
                binding.questionTwoTv.text = list.get(0)
                mQuestionTwoPosition = list.get(1).toInt()
            }

            Question.THREE.value -> {
                mQuestionThreeDialog.dismiss()
                binding.questionThreeTv.text = list.get(0)
                mQuestionThreePosition = list.get(1).toInt()
            }

            Question.FOUR.value -> {
                mQuestionFourDialog.dismiss()
                binding.questionFourTv.text = list.get(0)
                mQuestionFourPosition = list.get(1).toInt()
            }

            Question.FIVE.value -> {
                mQuestionFiveDialog.dismiss()
                binding.questionFiveTv.text = list.get(0)
                mQuestionFivePosition = list.get(1).toInt()
            }

            Question.SIX.value -> {
                mQuestionSixDialog.dismiss()
                binding.questionSixTv.text = list.get(0)
                mQuestionSixPosition = list.get(1).toInt()
            }

            Question.SEVEN.value -> {
                mQuestionSevenDialog.dismiss()
                binding.questionSevenTv.text = list.get(0)
                mQuestionSevenPosition = list.get(1).toInt()
            }
        }
    }

    enum class Question(val value: Int) {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7)
    }
}