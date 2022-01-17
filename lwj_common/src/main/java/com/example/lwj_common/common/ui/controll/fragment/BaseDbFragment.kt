package com.example.flashfeso_lwj.base.ui.controll.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.lwj_base.common.base.BaseConstants
import com.example.lwj_base.common.base.GetBinding

abstract class BaseDbFragment<T: ViewBinding>: Fragment(), GetBinding<T> {
    private  var _activity: Activity? = null
    private val DOUBLE_CLICK_TIME: Long = 500

    private var mFirstClickTime = 0L
    private var mSecondClickTime = 0L
    private  var _binding: T? = null
    protected val binding get() = _binding!!


    open var isReuse: Boolean = false //是否复用fragment

    protected var isInit = false//记录是否已经初始化过一次视图
    protected var lastView: View? = null//记录上次创建的view

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _activity = getActivity()
    }
    fun getFrgmActivity() = _activity as Context

    //protected fun getAtvContext(): Context = _activity?: App.instance!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeCreateView()
        observe()
        //在onCreate中执行,不会重写onCreateView,所已也可以达到复用视图的效果
        _binding = getViewBindingByReflex(layoutInflater)
        afterBindView()
        if(BaseConstants.ISLOG) Log.d("---ddd","onCreate" )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if(BaseConstants.ISLOG) Log.d("---ddd","onCreateView" )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isReuse){
            if(!isInit){
                if(BaseConstants.ISLOG) Log.d("---ddd","onViewCreatedWhenInit" )
                binding.initView()
                afterInitView()
                afterInitView(savedInstanceState)
                isInit = true
            }
        }else{
            if(BaseConstants.ISLOG) Log.d("---ddd","onViewCreated" )
            binding.initView()
            afterInitView()
            afterInitView(savedInstanceState)
        }
    }

    override fun onDestroyView() {
        if(BaseConstants.ISLOG) Log.d("---ddd","onDestoryView" )
        super.onDestroyView()
    }

    override fun onDetach() {
        if(BaseConstants.ISLOG) Log.d("---ddd","onDetach" )
        super.onDetach()
    }




    //两次点击的时间
    protected fun isClickUseful(): Boolean{
        mFirstClickTime = mSecondClickTime
        mSecondClickTime = System.currentTimeMillis()
        return mSecondClickTime - mFirstClickTime > DOUBLE_CLICK_TIME
    }

    protected abstract fun observe()

    //已有界面, 对界面内容做初始化工作
    abstract fun T.initView()

    //没有显示出界面但是已有bindnig(即已存界面的引用),可以设置监听
    protected open fun afterBindView() {}

    //加载数据(通常用于需要数据才能显示界面)
    protected open fun beforeCreateView() {}

    //拿到view之后创建适配器等
    protected open fun afterInitView( savedInstanceState: Bundle?){}

    //拿到view之后创建适配器等
    protected open fun afterInitView(){}

    override fun onDestroy() {
        super.onDestroy()
        //注意由于使用的是viewBinding, 千万不能回收binding对象, 否则会报空指针异常
        //_binding = null
        _activity = null
    }

    /**
     * Activity间跳转（不传值）
     *
     * @param cls 跳转的 Activity
     */
    protected open fun startActivity(cls: Class<*>?) {
        val intent = Intent(getFrgmActivity(), cls)
        this.startActivity(intent)
    }


    /**
     * Activity间的跳转（传值）
     *
     * @param cls    跳转的 Activity
     * @param bundle 传递参数
     */
    protected open fun startActivity(cls: Class<*>?, bundle: Bundle?) {
        val intent = Intent(getFrgmActivity(), cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        this.startActivity(intent)
    }

    /**
     * 隐藏软键盘
     * view  触发软键盘弹出的控件
     */
    open fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}