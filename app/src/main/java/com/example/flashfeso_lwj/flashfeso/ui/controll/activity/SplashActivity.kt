package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flashfeso_lwj.flashfeso.base.BaseDbActivity
import com.example.flashfeso_lwj.flashfeso.utils.SharedPreferenceUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashActivity : AppCompatActivity(){
    @Inject lateinit var  sharedPreferenceUtils: SharedPreferenceUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!this.isTaskRoot){//判断是否是任务栈中的根Activity, 如果是就不做任何处理, 如果不是即this.isTaskRoot为false, 直接finish掉
                             //第二次启动直接启动MainActivity
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        afterInit()
    }

    private fun afterInit() {
        if(sharedPreferenceUtils.isFirstLuanch()){
            //弹出对话框
        }else{
            sharedPreferenceUtils.setNotFirstLuanch()
        }
    }






}