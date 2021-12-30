package com.example.lwj_common.common.ui.controll.fragment

import androidx.viewbinding.ViewBinding
import com.example.flashfeso_lwj.base.ui.controll.fragment.BaseDbFragment

abstract class BaseRecyclerFragment<T: ViewBinding>:  BaseDbFragment<T>(){
    override var isReuse: Boolean = false
}