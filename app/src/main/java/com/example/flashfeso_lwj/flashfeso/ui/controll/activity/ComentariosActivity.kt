package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityComentariosBinding
import com.example.flashfeso_lwj.flashfeso.ui.controll.adapter.ERvComentariosListAdapter
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.ComentariosViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import com.example.lwj_common.common.ui.controll.tools.ktx.toTrim
import com.example.lwj_common.common.ui.controll.tools.utils.SystemServiceUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ComentariosActivity: BasePageStyleActivity<ActivityComentariosBinding>() {
    private val viewModel: ComentariosViewModel by viewModels()
    private val loginVieModel: LoginViewModel by viewModels()

    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null


    @Inject
    lateinit var mAdapter: ERvComentariosListAdapter


    override fun observe() {
        viewModel.customerFeedLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenClear {
                InfoUtil.clear()
                loginVieModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
            it.whenError {
                Toast.makeText(this@ComentariosActivity, it.msg, Toast.LENGTH_SHORT).show()
            }
            it.whenSuccess {
                if(it.msg.isUseful() && it.msg == resources.getString(R.string.success)) {
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                    binding.comentariosTv.setText("")
                    if(InfoUtil.isLogin) {
                        queryCustomerListFeed()
                    }
                }
            }
        })

        viewModel.customerFeedListLiveData.observe(this, Observer {
            it.whenError {
                binding.ercv.showError()
            }
            it.whenSuccess {
                //mAdapter.removeAll()
                mAdapter.clear()
                if(it.data != null && !it.data?.dataList.isNullOrEmpty()) {
                    mAdapter.addAll(it.data?.dataList)
                    //注意这里不能这样binding.ercv.requestLayout(), EasyRecyclerView不是RecyclerView
                    binding.ercv.recyclerView.requestLayout()
                } else {
                    binding.ercv.showEmpty()
                }
            }
            it.whenClear {
                InfoUtil.clear()
                loginVieModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        })
    }


    /* private fun refreshData() {
         binding.ercv.showProgress()
         viewModel.queryCustomerListFeed()
     }*/

    fun queryCustomerListFeed() {
        hideAllKeyBoard()
        binding.ercv.showProgress()

        val map: MutableMap<String, Any> = HashMap()
        map["page"] = 1
        map["size"] = 10000
        viewModel.queryCustomerListFeed(map as HashMap<String, Any>)
    }

    override fun ActivityComentariosBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.comentarios_susgerencias)

        if(InfoUtil.isLogin) {
            binding.historiaTv.visibility = View.VISIBLE
            binding.ercv.visibility = View.VISIBLE
            //mAdapter = ERvComentariosListAdapter(this@ComentariosActivity)
            // initItemDecoration();
            binding.ercv.setLayoutManager(LinearLayoutManager(this@ComentariosActivity))
            binding.ercv.adapter = mAdapter
            binding.ercv.setEmptyView(R.layout.incl_common_empty)
            binding.ercv.setErrorView(R.layout.incl_common_error)
            binding.ercv.setProgressView(R.layout.incl_common_loading)

            binding.ercv.setRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
                override fun onRefresh() {
                    queryCustomerListFeed()
                }
            })
        }
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.confirm.setOnClickListener {
            hideAllKeyBoard()
            if(!binding.comentariosTv.toString().trim().isUseful()) {
                Toast.makeText(this@ComentariosActivity, resources.getString(R.string.por_favor_introduza_su_retroalimentacion), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(isClickUseful()) {
                queryCustomerFeed(binding.comentariosTv.toTrim()!!)
            }
        }
    }


    fun queryCustomerFeed(leaveMessage: String) {
        mSimpleProgressDialogUtil?.showHUD(this, false)

        val map: MutableMap<String, Any> = HashMap()
        map["leaveMessage"] = leaveMessage
        viewModel.queryCustomerFeed(map as HashMap<String, Any>)
    }

    fun hideAllKeyBoard(){
        SystemServiceUtil.hideKeyboard(binding.comentariosTv)
    }

}