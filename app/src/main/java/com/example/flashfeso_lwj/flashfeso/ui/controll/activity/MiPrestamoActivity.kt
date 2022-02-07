package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityMiPrestamoBinding
import com.example.flashfeso_lwj.flashfeso.entity.OrderHistoryDataEntity
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.ERvMiPrestamoAdapter
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.MiPrestamoViewModel
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class MiPrestamoActivity: BasePageStyleActivity<ActivityMiPrestamoBinding>() {
    val viewModel: MiPrestamoViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var mAdapter: ERvMiPrestamoAdapter
    override fun observe() {
        viewModel.liveData.observe(this, Observer {
            it.whenError {
                binding.ercv.showError()
            }
            it.whenSuccess {

                mAdapter.clear()
                if(it.data!= null){
                    binding.ercv.recyclerView.requestLayout()
                }else{
                    binding.ercv.showEmpty()
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

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)


    }

    override fun afterInitView() {
        super.afterInitView()
        mAdapter.setOnItemClickListener(object: RecyclerArrayAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val orderHistoryDataBean: OrderHistoryDataEntity = mAdapter.allData.get(position)
                if(isClickUseful()) {
                    val intent = Intent(this@MiPrestamoActivity, DetallesDelPrestamoActivity::class.java)
                    intent.putExtra(DetallesDelPrestamoActivity.TAG_DATA_BEAN, orderHistoryDataBean)
                    startActivity(intent)
                }

            }

        })
    }

    override fun ActivityMiPrestamoBinding.initView() {
        header.tvCommonBarTitle.text = resources.getString(R.string.mi_prestamo)

        ercv.adapter = mAdapter
        ercv.setLayoutManager(LinearLayoutManager(this@MiPrestamoActivity))
        ercv.setEmptyView(R.layout.incl_common_empty)
        ercv.setErrorView(R.layout.incl_common_error)
        ercv.setProgressView(R.layout.incl_common_loading)

        ercv.setRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                queryOrderHistoryData()
            }
        })
        queryOrderHistoryData()
    }

    fun queryOrderHistoryData() {
        binding.ercv.showProgress()
        viewModel.query()
    }

}