package com.example.flashfeso_lwj.flashfeso.ui.controll.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.databinding.DialogSplashBinding
import com.example.flashfeso_lwj.flashfeso.event.SplashPermissionDialogEvent
import javax.inject.Inject

class SplashPermissionDialog @Inject constructor() : DialogFragment() {
    private lateinit var _binding: DialogSplashBinding
    val binding get() = _binding
    lateinit var mSplashPermissionDialogEvent: SplashPermissionDialogEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //若点击dialog覆盖不到的activity的空白或者按返回键，则调用cancel方法
        isCancelable = true
        //setStyle(STYLE_NO_TITLE, R.style.StyleCommonMultiDialog) //todo(不知道这行啥作用)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogSplashBinding.inflate(layoutInflater, container, false)
        initDialog()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //val style = R.style.StyleCommonMultiDialog //todo(自定义样式)
        return Dialog(requireContext(), 0)
    }

    private fun initDialog() {
        dialog?.let {
            it.window?.run {
                //setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                //setLayout(-1, -2)
            }
            it.setOnKeyListener(object : DialogInterface.OnKeyListener {
                override fun onKey(
                    dialogInterface: DialogInterface?,
                    keyCode: Int,
                    event: KeyEvent?,
                ): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        mSplashPermissionDialogEvent.cancelListener()
                        return true
                    } else {
                        return false
                    }
                }
            })

            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        binding.run {
            tvPermissionDialogConfirm.setOnClickListener { v->
                mSplashPermissionDialogEvent.confirmListener()
            }

            inclSplashContent1.let { incl ->
                incl.ivDialogPermissionLogo.setImageResource(R.drawable.ic_app_launcher)
                incl.tvDialogPermissionInfo.visibility = View.GONE
                incl.tvDialogPermissionContent.setText(R.string.description_title)
            }

            inclSplashContent2.let { incl ->
                incl.ivDialogPermissionLogo.setImageResource(R.drawable.icon_permission_camera)
                incl.tvDialogPermissionInfo.setText(R.string.description_camera_title)
                incl.tvDialogPermissionContent.setText(R.string.description_camera_contenido)
            }
            inclSplashContent3.let { incl ->
                incl.ivDialogPermissionLogo.setImageResource(R.drawable.icon_permission_storage)
                incl.tvDialogPermissionInfo.setText(R.string.description_almacenamiento_title)
                incl.tvDialogPermissionContent.setText(R.string.description_almacenamiento_contenido)
            }
            inclSplashContent4.let { incl ->
                incl.ivDialogPermissionLogo.setImageResource(R.drawable.icon_permission_phone)
                incl.tvDialogPermissionInfo.setText(R.string.description_telefono_title)
                incl.tvDialogPermissionContent.setText(R.string.description_telefono_contenido)
            }
            inclSplashContent5.let { incl ->
                incl.ivDialogPermissionLogo.setImageResource(R.drawable.icon_permission_maillist)
                incl.tvDialogPermissionInfo.setText(R.string.description_contactos_title)
                incl.tvDialogPermissionContent.setText(R.string.description_contactos_contenido)
            }
            inclSplashContent6.let { incl ->
                incl.ivDialogPermissionLogo.setImageResource(R.drawable.icon_permission_sms)
                incl.tvDialogPermissionInfo.setText(R.string.description_sms_title)
                incl.tvDialogPermissionContent.setText(R.string.description_sms_contenido)
            }
            inclSplashContent7.let { incl ->
                incl.ivDialogPermissionLogo.setImageResource(R.drawable.icon_permission_location)
                incl.tvDialogPermissionInfo.setText(R.string.description_localizacion_title)
                incl.tvDialogPermissionContent.setText(R.string.description_localizacion_contenido)
            }
            inclSplashContent8.let { incl ->
                incl.ivDialogPermissionLogo.setImageResource(R.drawable.icon_permission_phonestatus)
                incl.tvDialogPermissionInfo.setText(R.string.description_estado_del_telefono_title)
                incl.tvDialogPermissionContent.setText(R.string.description_estado_del_telefono_contenido)
            }
        }
    }
}