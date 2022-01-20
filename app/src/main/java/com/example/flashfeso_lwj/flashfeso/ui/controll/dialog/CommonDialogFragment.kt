package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.lwj_base.common.base.GetBinding

abstract class CommonDialogFragment<T: ViewBinding>: DialogFragment(), GetBinding<T> {
    private var _binding: T? = null
    val binding: T get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = isCancel()
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }

    abstract fun isCancel(): Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = getViewBindingByReflex(inflater)
        afterBindingView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initView()
        afterInitView()
    }

    abstract fun T.initView()
    /*
    若需要重写返回键监听, 在覆盖initView后写如下代码:
    dialog!!.setOnKeyListener { dialogInterface, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_BACK) {
                //do something
                true
            } else false
        }

     */

    protected open fun afterInitView() {

    }

    protected open fun afterBindingView() {

    }


}