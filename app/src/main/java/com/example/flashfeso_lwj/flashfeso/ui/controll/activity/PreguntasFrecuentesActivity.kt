package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityPreguntasFrecuentesBinding
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.ERvPreguntasAdapter
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.PreguntasFrecuentesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreguntasFrecuentesActivity: BasePageStyleActivity<ActivityPreguntasFrecuentesBinding>() {
    private val viewModel: PreguntasFrecuentesViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    lateinit var mAdapter: ERvPreguntasAdapter

    override fun observe() {

        viewModel.comPsLiveData.observe(this, Observer {
            it.whenError {
                binding.ercv.showError()
            }
            it.whenSuccess {
                mAdapter.clear()
                if(it.data != null && it.data?.isNullOrEmpty() != true){
                    mAdapter.addAll(it.data)
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

    override fun ActivityPreguntasFrecuentesBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.preguntas_frecuentes)
    }

    override fun afterInitView() {
        super.afterInitView()
        mAdapter = ERvPreguntasAdapter(this@PreguntasFrecuentesActivity)
        binding.ercv.setLayoutManager(LinearLayoutManager(this@PreguntasFrecuentesActivity))
        binding.ercv.adapter = mAdapter
        binding.ercv.setEmptyView(R.layout.incl_common_empty)
        binding.ercv.setErrorView(R.layout.incl_common_error)
        binding.ercv.setProgressView(R.layout.incl_common_loading)
        binding.ercv.setRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                queryComPs()
            }
        })
        queryComPs()
    }

    fun queryComPs() {
        binding.ercv.showProgress()
        viewModel.queryComPs()
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

    }

}