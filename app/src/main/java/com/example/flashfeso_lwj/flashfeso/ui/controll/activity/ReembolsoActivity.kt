package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityReembolsoBinding
import com.example.flashfeso_lwj.flashfeso.entity.CurrDetailEntity
import com.example.flashfeso_lwj.flashfeso.entity.PayChannelEntity
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.ERVPayChannelAdapter
import com.example.flashfeso_lwj.flashfeso.utils.Constants
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.AppNotifyViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.ReembolsoViewModel
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.utils.DoubleUtils
import com.example.lwj_common.common.ui.controll.tools.utils.NumberUtils
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Description:还款页面
 */
@AndroidEntryPoint
class ReembolsoActivity: BasePageStyleActivity<ActivityReembolsoBinding>() {
    var data: CurrDetailEntity? = null
    val viewModel: ReembolsoViewModel by viewModels()


    val loginViewModel: LoginViewModel by viewModels()
    val appNotifyViewModel: AppNotifyViewModel by viewModels()

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null

    @Inject
    lateinit var mAdapter: ERVPayChannelAdapter

    override fun onDestroy() {
        super.onDestroy()
        mSimpleProgressDialogUtil?.closeHUD()
    }

    private fun queryRepaymentSTP(loanId: String) {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val map: MutableMap<String, Any> = HashMap()
        map["loanId"] = loanId

        viewModel.queryRepaymentSTP(map as HashMap<String, Any>)
    }

    override fun observe() {

        viewModel.repaymentSTPLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenError {
                Toast.makeText(this@ReembolsoActivity, it.msg, Toast.LENGTH_LONG).show()
            }
            it.whenSuccess {
                if(it.data != null && it.data?.clabe.isUseful()) {
                    val intent = Intent(this@ReembolsoActivity, STPActivity::class.java)
                    intent.putExtra(STPActivity.TAG_CLABE, it.data)
                    startActivity(intent)
                }
            }
            it.whenClear {

                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this@ReembolsoActivity, it.msg, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })

        viewModel.repaymentOxxoLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenError {
                Toast.makeText(this@ReembolsoActivity, it.msg, Toast.LENGTH_LONG).show()

            }
            it.whenSuccess {

                if(it.data != null && it.data?.barcode_url.isUseful()) {
                    val intent = Intent(this@ReembolsoActivity, OxxoActivity::class.java)
                    intent.putExtra(OxxoActivity.TAG_BARCODE_URL, it.data?.barcode_url)
                    intent.putExtra(OxxoActivity.TAG_SERVICE_MONEY, it.data?.serviceMoney)
                    intent.putExtra(OxxoActivity.TAG_BARCODE, it.data?.barcode)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@ReembolsoActivity, it.msg, Toast.LENGTH_LONG).show()
                }
            }
            it.whenClear {

                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this@ReembolsoActivity, it.msg, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })

        appNotifyViewModel.notifyLiveData.observe(this, Observer {
            onBackPressed()
        })

        viewModel.payChannelLiveData.observe(this, Observer {
            it.whenError {
                showError()
            }
            it.whenSuccess {
                if(!it.data.isNullOrEmpty()) {
                    showSuccess()
                    val responseData = it.data
                    val list = ArrayList<PayChannelEntity>()
                    responseData?.forEachIndexed {index, ele ->
                        val ervData = PayChannelEntity()
                        if(index == 0) {
                            ervData.isSelector = true
                        }
                        ervData.payItem = ele
                        list.add(ervData)
                    }

                    if(!list.isNullOrEmpty()) {
                        mAdapter.removeAll()
                        mAdapter.clear()
                        mAdapter.addAll(list)
                        binding.depositErcv.requestLayout()
                    } else {
                        showError()
                    }
                } else {
                    showError()
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }


            }
            it.whenClear {

                InfoUtil.clear()
                loginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })
    }

    private fun queryRepaymentOxxo(loanId: String) {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val map: MutableMap<String, Any> = HashMap()
        map["loanId"] = loanId
        viewModel.queryRepaymentOxxo(map as HashMap<String, Any>)
    }

    override fun afterInitView() {
        super.afterInitView()
        binding.depositErcv.setLayoutManager(object: GridLayoutManager(this@ReembolsoActivity, 3) {
            override fun canScrollHorizontally(): Boolean = false
            override fun canScrollVertically(): Boolean = false
        })
        binding.depositErcv.adapter = mAdapter
    }

    fun initData() {
        showProgress()
        if(data != null) {
            val money = resources.getString(R.string.money_symbol)
            val días = resources.getString(R.string.días)

            if(data?.repaymentAmount.isUseful()) {
                binding.repayMoneyTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divToString(data?.repaymentAmount, "100", 2)))
                binding.repayMoneyTvTwo.setText(money + NumberUtils.goToZeroString(DoubleUtils.divToString(data?.repaymentAmount, "100", 2)))
            } else {
                binding.repayMoneyTv.text = ""
                binding.repayMoneyTvTwo.text = ""
            }

            if(data?.loanAmount.isUseful()) {
                binding.loanAmountTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divToString(data?.loanAmount, "100", 2)))
            } else {
                binding.loanAmountTv.text = ""
            }

            if(!StringUtils.isEmpty(data?.interest)) {
                binding.interestTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divToString(data?.interest, "100", 2)))
            } else {
                binding.interestTv.text = ""
            }

            if(!StringUtils.isEmpty(data?.repaidMoney)) {
                binding.cantidadDevueltaTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divToString(data?.repaidMoney, "100", 2)))
            } else {
                binding.cantidadDevueltaTv.text = ""
            }

            if(!StringUtils.isEmpty(data?.remainderMoney)) {
                binding.reembolsoPendienteTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divToString(data?.remainderMoney, "100", 2)))
            } else {
                binding.reembolsoPendienteTv.text = ""
            }

            if(!StringUtils.isEmpty(data?.repayDate)) {
                binding.repayDateTv.setText(data?.repayDate)
                binding.repayDateLl.visibility = View.VISIBLE
                binding.repayDateTvTwo.setText(data?.repayDate)
                binding.repayDateLlTwo.visibility = View.VISIBLE
            } else {
                binding.repayDateTv.text = ""
                binding.repayDateLl.visibility = View.GONE
                binding.repayDateTvTwo.text = ""
                binding.repayDateLlTwo.visibility = View.GONE
            }

            if(!StringUtils.isEmpty(data?.overdueAmount)) {
                binding.overdueHandFeeTv.setText(money + NumberUtils.goToZeroString(DoubleUtils.divToString(data?.overdueAmount, "100", 2)))
                binding.overdueHandFeeLl.visibility = View.VISIBLE
                binding.repayDateLlTwo.visibility = View.GONE
                binding.repayDateLl.visibility = View.VISIBLE
            } else {
                binding.overdueHandFeeTv.text = ""
                binding.overdueHandFeeLl.visibility = View.GONE
                binding.repayDateLlTwo.visibility = View.VISIBLE
                binding.repayDateLl.visibility = View.GONE
            }

            if(!StringUtils.isEmpty(data?.overdueDays)) {
                binding.overdueDaysTv.setText(data?.overdueDays.toString() + " " + días)
                binding.overdueDaysLl.visibility = View.VISIBLE
            } else {
                binding.overdueDaysTv.text = ""
                binding.overdueDaysLl.visibility = View.GONE
            }

            queryPayChannel()
        } else {

            showEmpty()
        }
    }


    override fun ActivityReembolsoBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.reembolso)
        initData()

    }

    private fun showEmpty() {
        binding.empty.viewEmpty.visibility = View.VISIBLE
        binding.error.llError.visibility = View.GONE
        binding.progress.llProgress.visibility = View.GONE

    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.error.viewErrorUpdate.setOnClickListener {
            if(isClickUseful()) {
                initData()
            }
        }

        binding.repayBtn.setOnClickListener {
            if(isClickUseful() && data != null && data?.loanId.isUseful()) {
                val allData: List<PayChannelEntity> = mAdapter.allData
                var channel = ""
                for(i in allData.indices) {
                    if(allData[i].isSelector) {
                        channel = allData[i].payItem!!
                        break
                    }
                }

                if(StringUtils.equals(channel, Constants.PAY_CHANNEL_OPENPAY)) {
                    val intent = Intent(this@ReembolsoActivity, OpenPayActivity::class.java)
                    intent.putExtra(OpenPayActivity.TAG_OPENPAY, data?.loanId)
                    startActivity(intent)
                } else if(StringUtils.equals(channel, Constants.PAY_CHANNEL_STP)) {
                    data?.loanId?.let {it1 -> queryRepaymentSTP(it1)}
                } else if(StringUtils.equals(channel, Constants.PAY_CHANNEL_OXXO)) {
                    data?.loanId?.let {queryRepaymentOxxo(it)}
                }
            }
        }


    }

    override fun afterInitView(savedInstanceState: Bundle?) {
        super.afterInitView(savedInstanceState)
        mAdapter.setOnItemClickListener {position ->
            for(i in 0 until mAdapter.allData.size) {
                mAdapter.allData[i].isSelector = i == position
            }
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun beforeCreateView() {
        super.beforeCreateView()
        data = intent.getParcelableExtra("currDetailsBean")
    }

    fun queryPayChannel() {

        viewModel.queryPayChannel()
    }

    fun showProgress() {
        binding.progress.llProgress.visibility = View.VISIBLE
        binding.error.llError.visibility = View.GONE
        binding.empty.viewEmpty.visibility = View.GONE
    }

    fun showSuccess() {
        binding.progress.llProgress.visibility = View.GONE
        binding.error.llError.visibility = View.GONE
        binding.empty.viewEmpty.visibility = View.GONE
    }

    fun showError() {
        binding.progress.llProgress.visibility = View.GONE
        binding.error.llError.visibility = View.VISIBLE
        binding.empty.viewEmpty.visibility = View.GONE
        binding.error.viewErrorUpdate.visibility = View.VISIBLE
    }

}