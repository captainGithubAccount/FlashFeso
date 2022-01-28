package com.example.flashfeso_lwj.flashfeso.ui.controll.fragment

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.flashfeso_lwj.base.ui.controll.fragment.BaseDbFragment
import com.example.flashfeso_lwj.common.ui.controll.dialog.CommonDialog
import com.example.flashfeso_lwj.databinding.FragmentMainMicuentaBinding
import com.example.flashfeso_lwj.flashfeso.ui.controll.activity.*
import com.example.flashfeso_lwj.flashfeso.utils.InfoUtil
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_common.common.ui.controll.tools.ktx.isUseful
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMiCuentaFragment: BaseDbFragment<FragmentMainMicuentaBinding>() {
    val loginViewModel: LoginViewModel by viewModels()

    companion object {
        //注意该方法获取的不是单例, 该项目中只调用一次该方法, 所以效果也是只有一个对象创建
        fun getInstance(): MainMiCuentaFragment = MainMiCuentaFragment()


    }

    override fun observe() {
        loginViewModel.notifyUpdateLoginLiveData.observe(this, Observer {
            if(InfoUtil.isLogin) {
                binding.logOut.visibility = View.VISIBLE
                val acountPhone = InfoUtil.getAccount() //这里的acount实际上就是电话号码
                if(!acountPhone.isUseful() && acountPhone?.length !! >= 10) {
                    val leftSubStr = acountPhone.substring(0, 3)
                    val rightSubStr = acountPhone.substring(7)
                    binding.meAccount.text = leftSubStr.plus("****").plus(rightSubStr)
                } else {
                    InfoUtil.clear()
                    binding.meAccount.text = resources.getString(R.string.unlogin_text)
                    binding.logOut.visibility = View.GONE
                }
            } else {
                binding.logOut.visibility = View.GONE
                binding.meAccount.text = getFrgmActivity().resources.getString(R.string.unlogin_text)
            }
        })
    }

    override fun FragmentMainMicuentaBinding.initView() {


    }

    /**
     * fragment的隐藏与重新展示的调用，此时不走onResume(fragment第一次创建时不会调用)
     * @param hidden true：隐藏  false：展示
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isLogin()
    }

    /**
     * onHiddenChanged仅仅用于切换frgm时候调用, 当点击返回键时候回来不会调用onHiddenChanged方法
     * 所以需要重写onResume方法
     * */
    override fun onResume() {
        super.onResume()
        isLogin()
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.logOut.setOnClickListener {
            val dialog = CommonDialog(getFrgmActivity().resources.getString(R.string.esta_seguro))
            dialog.mCommonDialogEvent = object: CommonDialogEvent {
                override fun onCancel() {
                    dialog.dismiss()
                }

                override fun onConfirm() {
                    dialog.dismiss()
                    InfoUtil.clear()
                    loginViewModel.queryNotifyUpdateLoginLiveData()
                    isLogin()
                }
            }
            dialog.show(parentFragmentManager, "MainMiCuentaFrgm")
        }

        binding.meHeaderImg.setOnClickListener {
            if(isClickUseful() && ! InfoUtil.isLogin) {
                startActivity(Intent(getFrgmActivity(), LoginActivity::class.java))
            }
        }

        //账户
        binding.meAccount.setOnClickListener {
            if(isClickUseful() && !InfoUtil.isLogin) {
                startActivity(Intent(getFrgmActivity(), LoginActivity::class.java))
            }
        }

        //意见反馈界面(需登录)
        binding.comentariosLl.setOnClickListener {
            if(isClickUseful()) {
                if(InfoUtil.isLogin) {
                    startActivity(Intent(getFrgmActivity(), ComentariosActivity::class.java))
                } else {
                    startActivity(LoginActivity::class.java)
                }
            }
        }

        //常见问题界面
        binding.preguntasLl.setOnClickListener {
            if(isClickUseful()) {
                startActivity(PreguntasFrecuentesActivity::class.java)
            }

        }

        //客户支持界面
        binding.configuracionLl.setOnClickListener {
            if(isClickUseful()){
                startActivity(ConfiguracionActivity::class.java)
            }
        }

        //贷款列表界面(需登录)
        binding.historialLl.setOnClickListener {
            if(isClickUseful()){
                if(InfoUtil.isLogin){
                    startActivity(MiPrestamoActivity::class.java)
                }else{
                    startActivity(LoginActivity::class.java)
                }
            }
        }
    }

    override fun afterInitView() {
        super.afterInitView()
        isLogin()
    }

    private fun isLogin() {
        if(InfoUtil.isLogin) {
            binding.logOut.visibility = View.VISIBLE
            val acountPhone = InfoUtil.getAccount() //这里的acount实际上就是电话号码
            if(acountPhone.isUseful() && acountPhone?.length!! >= 10) {
                val leftSubStr = acountPhone.substring(0, 3)
                val rightSubStr = acountPhone.substring(7)
                binding.meAccount.text = leftSubStr.plus("****").plus(rightSubStr)
            } else {
                InfoUtil.clear()
                binding.meAccount.text = resources.getString(R.string.unlogin_text)
                binding.logOut.visibility = View.GONE
            }
        } else {
            binding.logOut.visibility = View.GONE
            binding.meAccount.text = getFrgmActivity().resources.getString(R.string.unlogin_text)
        }
    }

    override var isReuse: Boolean = false

}