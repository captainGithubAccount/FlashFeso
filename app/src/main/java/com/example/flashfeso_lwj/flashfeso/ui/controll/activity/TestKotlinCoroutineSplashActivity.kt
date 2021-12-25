package com.example.flashfeso_lwj.flashfeso.ui.controll.activity



import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.flashfeso_lwj.BuildConfig
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.event.CommonDialogEvent
import com.example.flashfeso_lwj.common.ui.controll.dialog.CommonDialog
import com.example.flashfeso_lwj.flashfeso.event.SplashPermissionDialogEvent
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.SplashPermissionDialog
import com.example.flashfeso_lwj.flashfeso.utils.*
import com.example.flashfeso_lwj.flashfeso.viewmodel.SplashViewModel
import com.example.lwj_common.common.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject


@AndroidEntryPoint
class TestKotlinCoroutineSplashActivity : AppCompatActivity(), SplashPermissionDialogEvent {
    private var mFirstClick: Long = 0
    private var mSecondClick: Long = 0

    //测试job是否取消
    lateinit var job: Job

    @Inject
    lateinit var mSharedPreferenceUtils: SharedPreferenceUtils
    @Inject
    lateinit var mSplashPermissionDialog: SplashPermissionDialog

    val splashViewModel: SplashViewModel by viewModels()
    //    方式二: 绑定生命周期
    init{
        lifecycleScope.launchWhenCreated {

            /*supervisorScope {
                *//*launch {
                    repeat(5){
                        //delay(100)

                        Log.d("test", "${test++}")
                    }

                }*//*
                *//*launch {


                    whenObserve()
                }*//*
                whenObserve()
            }*/


            whenObserve()
            /*Log.d("---before delay", "job.isCancelled: ${job.isCancelled.toString()} -- job.isActive: ${job.isActive.toString()} -- job.isCompleted:${job.isCompleted} ")
            delay(3000)
            Log.d("---after delay", "job.isCancelled: ${job.isCancelled.toString()} -- job.isActive: ${job.isActive.toString()} -- job.isCompleted:${job.isCompleted} ")*/

        }

    }

    //todo test
    override fun onDestroy() {
        super.onDestroy()
        //todo 测试job是否取消
        //job.cancel()

        Log.d("---job is canceld", "job.isCancelled: ${job.isCancelled.toString()} -- job.isActive: ${job.isActive.toString()} -- job.isCompleted:${job.isCompleted} ")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //判断是否是任务栈中的根Activity, 如果是就不做任何处理, 如果不是即this.isTaskRoot为false, 直接finish掉
        //第二次启动直接启动MainActivity
        if (!this.isTaskRoot)
            finish()
//        方式一: 绑定生命周期
        /*observeWhenCreated {
            whenObserve()
        }*/

        //whenObserve()
        initEvent()
    }

    //todo 该方法测试中, 意图给协程与生命周期关联
    fun observeWhenCreated(block: () -> Unit) = lifecycleScope.launchWhenCreated {
        block.invoke()
    }

    //todo test
    var test = 0
    private suspend fun whenObserve() {
        //数据带状态的实现
        splashViewModel.dataLiveData.observe(this, Observer { statedata ->
            statedata.whenSuccessAndDefaultErrorDeal { versionData ->
                if(versionData != null){

                    Log.d("first data", versionData.toString())
                }else{
                    Log.d("first data", "null")

                }
                versionData?.let {
                    if (!StringUtils.isEmpty(versionData.VId)) {
                        if (versionData.VId.toLong() > BuildConfig.VERSION_CODE) {
                            if (versionData.isUpdate) {
                                CommonDialog(resources.getString(R.string.new_version_found))
                                    .apply {
                                        mCommonDialogEvent = object : CommonDialogEvent {
                                            override fun cancelListener() {
                                                dismiss()
                                                finish()
                                            }
                                            override fun confirmListener() {
                                                openBrowser(requireActivity(),
                                                    versionData.downloadURl)
                                            }
                                        }
                                    }.show(supportFragmentManager, "CommonDialogFragment")
                                //commonDialog.show(supportFragmentManager, "CommonDialogFragment")
                            } else {
                                CommonDialog(resources.getString(R.string.new_version_found))
                                    .apply {
                                        mCommonDialogEvent = object : CommonDialogEvent {
                                            override fun cancelListener() {
                                                jumpToMainActivity()
                                            }
                                            override fun confirmListener() {
                                                openBrowser(requireActivity(),
                                                    versionData.downloadURl)
                                            }
                                        }
                                    }
                                    .show(supportFragmentManager, "CommonDialogFragment2")
                            }
                        } else {
                            jumpToMainActivity()
                            //todo 测试job是否取消
                            finish()
                        }
                    } else {
                        jumpToMainActivity()
                    }
                }
            }

        })

        //数据不带状态的实现
        /*splashViewModel.versionLiveData.observe(this, Observer{ versionResponse ->
            Log.d("SplashActivity",versionResponse.data.toString())
            if(versionResponse.code == 200 && versionResponse.data != null && !StringUtils.isEmpty(versionResponse.data.VId)){
                versionResponse.data.let{ data ->
                    if(data.VId.toLong() > BuildConfig.VERSION_CODE){
                        if(data.isUpdate){
                            CommonDialog(resources.getString(R.string.new_version_found))
                                .apply {
                                    mCommonDialogEvent = object : CommonDialogEvent{
                                        override fun cancelListener() {
                                            dismiss()
                                            finish()
                                        }
                                        override fun confirmListener() {
                                            openBrowser(requireActivity(), data.downloadURl)
                                        }
                                    }
                                }.show(supportFragmentManager, "CommonDialogFragment")
                             //commonDialog.show(supportFragmentManager, "CommonDialogFragment")
                        }else{
                            CommonDialog(resources.getString(R.string.new_version_found))
                                .apply {
                                    mCommonDialogEvent = object : CommonDialogEvent{
                                        override fun cancelListener() {
                                            jumpToMainActivity()
                                        }
                                        override fun confirmListener() {
                                            openBrowser(requireActivity(), data.downloadURl)
                                        }
                                    }
                                }
                                .show(supportFragmentManager, "CommonDialogFragment2")
                        }
                    }else{
                        jumpToMainActivity()
                    }
                }
            }else{
                jumpToMainActivity()
            }
        })*/
    }

    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    private fun openBrowser(context: Context, browserUrl: String) {
        val intent = Intent()
        intent.setAction(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(browserUrl))
        //启动下载当前应用的app链接
        startActivity(Intent.createChooser(intent,
            context.getResources().getString(R.string.choose_browser)))

        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        /*if (intent.resolveActivity(context.packageManager) != null) {
            val componentName = intent.resolveActivity(context.packageManager)
            context.startActivity(Intent.createChooser(intent,
                context.resources.getString(R.string.choose_browser)))
        } else {
            Toast.makeText(context.applicationContext,
                context.resources.getString(R.string.download_browser),
                Toast.LENGTH_SHORT).show()
        }*/
    }

    //todo 待实现Main
    private fun jumpToMainActivity() {

        Log.d("---", "test")
        mFirstClick = mSecondClick
        mSecondClick = System.currentTimeMillis()
        if (mSecondClick - mFirstClick > Constants.DOUBLE_CLICK_TIME) {
            startActivity(Intent(this@TestKotlinCoroutineSplashActivity, MainActivity::class.java))
        }

    }

    private fun initEvent() {
        mSplashPermissionDialog.mSplashPermissionDialogEvent = this
    }


    override fun onStart() {
        super.onStart()
        //注意这里不能在onStart中执行afterInit方法, 否则可能不能获取到数据, 导致检查完权限后无法进入下一个界面
        //onStart到onResume。这个阶段，Activity被创建，布局已加载，但是界面还没绘制，可以说界面都不存在(也就是布局有了还没开始绘制)
        //
        //个人感觉原因: 当网络获取数据较快时候, 如在绘制之前已经拿到了数据, 这个时候进入观察中, 但是无法绘制(具体绘制要在执行onResume方法时)
        //所以第一次无法弹出观察中的方法里面的dialog, 但是这个时候再次运行就可以看到dialog了
        //afterInit()
    }

    override fun onResume() {
        super.onResume()
        afterInit()
    }

    private fun afterInit() {
        //todo(为了测试用的, 每次初始化将sp值改为true, 这样每次都是第一次登录)
        //mSharedPreferenceUtils.mSharedPreferences.edit().putBoolean("isFirstLuanch", true).apply()

        if (mSharedPreferenceUtils.isFirstLuanch()) {
            //弹出对话框
            mSplashPermissionDialog.show(supportFragmentManager, "SplashPermissionDialog")
        } else {
            //检查权限
            checkAppPermission(this@TestKotlinCoroutineSplashActivity)
        }
    }


    //处理权限结果回掉方法(该方法在Activity/Fragment中应该被重写，当用户处理完授权操作时，系统会自动回调该方法)
    // ActivityCompat.requestPermissions(activity, APP_PERMISSIONS, PERMISSION_REQUEST_CODE)方法后回掉
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                var isAllPermissionGranted = true
                if (grantResults.size > 0) {
                    //对所有的权限进行遍历, 判断是否所有的权限都授权了
                    grantResults.forEachIndexed { index, ele ->
                        if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                            isAllPermissionGranted = false
                        }
                    }
                }
                if (!isAllPermissionGranted) {
                    //若有权限未授权, 打开系统信息应用界面
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    startActivity(intent.apply {
                        intent.setData(Uri.parse("package:${packageName}"))
                    })
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun cancelListener() {
        //低版本直接调用finish关闭Activity, 高版本首先去关闭ActionBar的展开菜单, 然后对FragmentManager进行退栈操作, 最后关闭Activity
        onBackPressed()
        mSplashPermissionDialog.dismiss()
    }

    override fun confirmListener() {
        //点击对话框确定按钮, 打开系统请求权限对话框
        mSharedPreferenceUtils.setNotFirstLuanch()
        mSplashPermissionDialog.dismiss()
        checkAppPermission(this@TestKotlinCoroutineSplashActivity)
    }


    private fun checkAppPermission(activity: Activity) {
        try {
            //拿到对应的权限名
            val permission =
                ActivityCompat.checkSelfPermission(activity, "android.permission.INTERNET")
            val permission2 = ActivityCompat.checkSelfPermission(activity,
                "android.permission.WRITE_EXTERNAL_STORAGE")
            val permission3 = ActivityCompat.checkSelfPermission(activity,
                "android.permission.READ_EXTERNAL_STORAGE")
            val permission4 =
                ActivityCompat.checkSelfPermission(activity, "android.permission.READ_CONTACTS")
            val permission5 =
                ActivityCompat.checkSelfPermission(activity, "android.permission.READ_PHONE_STATE")
            val permission7 = ActivityCompat.checkSelfPermission(activity,
                "android.permission.ACCESS_NETWORK_STATE")
            val permission8 =
                ActivityCompat.checkSelfPermission(activity, "android.permission.ACCESS_WIFI_STATE")
            val permission9 = ActivityCompat.checkSelfPermission(activity,
                "android.permission.ACCESS_FINE_LOCATION")
            val permission10 = ActivityCompat.checkSelfPermission(activity,
                "android.permission.ACCESS_COARSE_LOCATION")
            val permission12 =
                ActivityCompat.checkSelfPermission(activity, "android.permission.CAMERA")
            val permission13 =
                ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_CONTACTS")
            val permission14 =
                ActivityCompat.checkSelfPermission(activity, "android.permission.READ_SMS")
            //检测上面权限是否全部被授予
            if (permission != PackageManager.PERMISSION_GRANTED
                || permission2 != PackageManager.PERMISSION_GRANTED
                || permission3 != PackageManager.PERMISSION_GRANTED
                || permission4 != PackageManager.PERMISSION_GRANTED
                || permission5 != PackageManager.PERMISSION_GRANTED
                || permission7 != PackageManager.PERMISSION_GRANTED
                || permission8 != PackageManager.PERMISSION_GRANTED
                || permission9 != PackageManager.PERMISSION_GRANTED
                || permission10 != PackageManager.PERMISSION_GRANTED
                || permission12 != PackageManager.PERMISSION_GRANTED
                || permission13 != PackageManager.PERMISSION_GRANTED
                || permission14 != PackageManager.PERMISSION_GRANTED
            ) {
                //若有权限未授予, 会弹出系统自带的申请权限对话框
                ActivityCompat.requestPermissions(activity,
                    APP_PERMISSIONS,
                    PERMISSION_REQUEST_CODE)
            } else {
                //适配小米手机, 但是小米出现情况很少可忽略
                //checkXiaoMiSms()

                getVersionLatestData()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getVersionLatestData() {
        //没有状态的livedata数据通数据
        //splashViewModel.query()

        //有状态的livedata数据通数据
        splashViewModel.query2()
    }


}