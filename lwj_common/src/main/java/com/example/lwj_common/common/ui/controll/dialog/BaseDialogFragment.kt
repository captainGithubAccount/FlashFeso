package com.example.lwj_common.common.ui.controll.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.lwj_base.common.base.GetBinding

/**
 * @author lwj
 * */
abstract class BaseDialogFragment<T: ViewBinding>: DialogFragment(), GetBinding<T> {
    private var _binding: T? = null
    val binding: T get() = _binding!!

    var listener: CommonDialogEvent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = isCancel()
        /*
        注意: 当isCancelable为false时候等同于设置
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

         */
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
                do something
                true
            } else false
        }

     */

    protected open fun afterInitView() {

    }

    protected open fun afterBindingView() {

    }
}

/* Use

class JumpSysytemLocDialog(): CommonDialogFragment<DialogJumpSysytemLocBinding>() {
    var listener: CommonDialogEvent? = null
    override fun DialogJumpSysytemLocBinding.initView() {}
    override fun afterBindingView() {
        super.afterBindingView()
        binding.btnCancel.setOnClickListener {
            listener?.onCancel()
        }
        binding.btnConfirm.setOnClickListener {
            listener?.onConfirm()
        }
    }
    override fun isCancel(): Boolean = false
}

 */