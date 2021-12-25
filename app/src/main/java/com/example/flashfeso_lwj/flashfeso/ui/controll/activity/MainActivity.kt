package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.graphics.Rect
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.ActivityMainBinding
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.flashfeso.ui.controll.fragment.MainInicioFragment
import com.example.flashfeso_lwj.flashfeso.ui.controll.fragment.MainMiCuentaFragment
import com.example.lwj_common.common.utils.DensityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BasePageStyleActivity<ActivityMainBinding>(){
    //注意fragmentTransaction和fragmentmanager实例化需要在局部方法中实例化, 不能为全局方法
    private lateinit var _fragmentTransacation: FragmentTransaction
    private var mInicioFragment: MainInicioFragment? = null
    private var mMiCuentaFragment: MainMiCuentaFragment? = null

    override fun ActivityMainBinding.initView() {
        /*binding.tvTest.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }*/

    }

    override fun afterBindView() {
        super.afterBindView()
        setSize(binding.rbMainBottomInfo)
        setSize(binding.rbMainBottomMe)

        binding.rgMainBottom.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                _fragmentTransacation = supportFragmentManager.beginTransaction()

                hideAllFragment(_fragmentTransacation)

                when(checkedId){
                    R.id.rb_main_bottom_info -> {
                        if(mInicioFragment == null){
                            mInicioFragment = MainInicioFragment.getInstance()
                            showOneFragment(mInicioFragment!!, true)
                        }else{
                            showOneFragment(mInicioFragment!!, false)
                        }
                    }
                    R.id.rb_main_bottom_me -> {
                        if(mMiCuentaFragment == null){
                            mMiCuentaFragment = MainMiCuentaFragment.getInstance()
                            showOneFragment(mMiCuentaFragment!!, true)
                        }else{
                            showOneFragment(mMiCuentaFragment!!, false)
                        }
                    }
                }
            }
        })

        //注意这里不能在布局里面指定第一个radioButton checked属性为true否则, 一开始需要显示的fragment界面为空白
        binding.rbMainBottomInfo.isChecked = true

    }

    private fun hideAllFragment(fragmentTransaction: FragmentTransaction) {
        mInicioFragment?.let { fragmentTransaction.hide(it) }
        mMiCuentaFragment?.let{ fragmentTransaction.hide(it) }
    }

    private fun showOneFragment(fragment: Fragment, isNeedAdd: Boolean) {
        if(isNeedAdd){
            _fragmentTransacation.add(R.id.fl_main_frgm_container,fragment)
        }
        _fragmentTransacation.show(fragment)
        //注意: 是用commit()提交还是commitAllowingStateLoss()根据场景来决定
        _fragmentTransacation.commit()
    }


    override fun observe() {

    }

    //设置RadioButton的drawableTop大小, 防止底部导航图片位置错乱, 但是只要切图好的话是不会有这种情况的
    private fun setSize(rb: RadioButton) {
        val rect = Rect()
        //距离父窗体的距离，可以理解为左上和右下的坐标
        rect[0, 0, DensityUtil.dip2px(this, 20F)] =
            DensityUtil.dip2px(this, 20F)
        val drawables = rb.compoundDrawables
        //取出上边的图片设置大小
        drawables[1].bounds = rect
        //把这张图片放在上边，这四个参表示图片放在左、上、有、下
        rb.setCompoundDrawables(null, drawables[1], null, null)
    }

}
