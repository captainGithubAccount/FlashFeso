package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.databinding.ActivityInformacionDeIdetidadBinding
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.CommonSelectDialog
import com.example.flashfeso_lwj.flashfeso.ui.controll.view.TakePhotoPopupWindow
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.InformacionDeldetidadMobiRecordViewModel
import com.example.lwj_common.common.camera.GlideEngine
import com.example.lwj_common.common.ui.controll.event.CommonItemOnclickListener
import com.example.lwj_common.common.ui.controll.tools.utils.FileUtil
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.language.LanguageConfig
import com.yalantis.ucrop.view.OverlayView.FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH
import java.io.File
import kotlin.collections.HashMap

class InformacionDeIdetidadActivity: BasePageStyleActivity<ActivityInformacionDeIdetidadBinding>() {

    private val FRENTA_DE_INE = 999
    private val DETRAS_DE_INE = 1000
    private val SELFIES_DE_TU_ROSTRO_COMPLETP = 1001

    private val SELECR_IMAGE_FRENTA_DE_INE = 99
    private val SELECR_IMAGE_DETRAS_DE_INE = 100
    private val SELECR_IMAGE_SELFIES_DE_TU_ROSTRO_COMPLETP = 101


    private val REQUEST_CODE_LIVENESS = 102
    private val REQUEST_CODE_MOBI_LIVENESS = 103

    private val viewModel: InformacionDeldetidadMobiRecordViewModel by viewModels()
    lateinit var mFirstCommonSelectDialog: CommonSelectDialog
    private val frentaDeIneUrl = ""
    private val detrasDeIneUrl = ""
    private val selfiesDeTuUrl = ""
    override fun observe() {
        viewModel.liveData.observe(this, Observer {
            it.whenError {
                queryMobiRecord("001：Click the button on the front of the ID card!")
            }
        })


    }

    override fun ActivityInformacionDeIdetidadBinding.initView() {
        binding.header.tvCommonBarTitle.text = resources.getString(R.string.informacion_de_idetidad)
    }

    override fun afterBindView() {
        super.afterBindView()
        binding.header.ivCommonBarBack.back(this)

        binding.franteImg.setOnClickListener {
            if(isClickUseful()){
                hideKeyboardAll()
                //调接口打点,后台可以根据这个接口被调用来判断该用户目前在做什么来判断用户的操作
                queryMobiRecord("001：Click the button on the front of the ID card!")
                //底部弹出popupWindow用来打开相册, 选择图片等
                val takePhotoWindow = TakePhotoPopupWindow(this)
                takePhotoWindow.setListener(object: CommonItemOnclickListener {
                    override fun onItemOnclickListener(position: Int) {
                        if(position == TvPosition.TAKE_PHOTO.ordinal){
                            openCameraTakePhoto(FRENTA_DE_INE)
                        }else if(position == TvPosition.SELECT_PICTURE.ordinal){
                            openGallerySelectImage(SELECR_IMAGE_FRENTA_DE_INE)
                        }
                        takePhotoWindow.dismiss()
                    }
                })
                takePhotoWindow.showInScreenBottom()
            }
        }

        binding.detrasImg.setOnClickListener {
            hideKeyboardAll()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            when(requestCode){
                FRENTA_DE_INE, SELECR_IMAGE_FRENTA_DE_INE -> {
                    //图片结果回掉
                    val selectList: List<LocalMedia>? = PictureSelector.obtainMultipleResult(data)
                    if(selectList != null && selectList.size > 0){
                        //由于是单选, 所以只获取到一张图片, 不需要遍历获取图片集
                        val media = selectList[0]
                        if(media.isCompressed){
                            val compressPath: String = media.compressPath
                            //decodeFile(String pathName, Options opts)：从 pathName 指定的文件中解析、创建 Bitmap 对象。
                            val bitmap: Bitmap = BitmapFactory.decodeFile(FileUtil.checkDirPath(compressPath))
                            val file: File = File(FileUtil.checkDirPath(compressPath))
                            uploadImage("front",file ,compressPath, bitmap)
                        }
                    }

                }
            }
        }
    }

    private fun uploadImage(type: String, file: File, filePath: String, bitmap: Bitmap) {
        doing
    }


    private fun openGallerySelectImage(requestCode: Int) {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .selectionMode(PictureConfig.SINGLE)
            .isEnableCrop(true)
            .freeStyleCropMode(FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH)
            .isPreviewImage(true)
            .isCompress(true)
            /*.isCamera(false)*///显示or隐藏拍摄  false: 隐藏(仅仅当openGallery时候才有作用)
            .hideBottomControls(false)
            .imageEngine(GlideEngine.createGlideEngine())
            .setLanguage(LanguageConfig.SPANISH)
            .forResult(requestCode)
    }



    private fun openCameraTakePhoto(requestCode: Int) {
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofImage())
            .selectionMode(PictureConfig.SINGLE)
            .isEnableCrop(true)
            .freeStyleCropMode(FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH)
            .isPreviewImage(true)
            .isCompress(true)
            .isCamera(false)//显示or隐藏拍摄  false: 隐藏(仅仅当openGallery时候才有作用)
            .hideBottomControls(false)
            .imageEngine(GlideEngine.createGlideEngine())
            .setLanguage(LanguageConfig.SPANISH)
            .forResult(requestCode)
    }

    enum class TvPosition {
        ZERO, TAKE_PHOTO, SELECT_PICTURE, CANCEL
    }

    private fun queryMobiRecord(event: String){
        val map: MutableMap<String, Any> = HashMap()
        map["event"] = event
        viewModel.query(map as HashMap<String, Any>)

    }

    private fun hideKeyboardAll() {
        if(binding.curpTv.hasFocus()){
            val imm = binding.curpTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.curpTv.windowToken, 0)
        }
        if(binding.apellidoPaternoTv.hasFocus()){
            val imm = binding.apellidoPaternoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.apellidoPaternoTv.windowToken, 0)
        }
        if(binding.apellidoMaternoTv.hasFocus()){
            val imm = binding.apellidoMaternoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.apellidoMaternoTv.windowToken, 0)
        }
        if(binding.nombreCompletoTv.hasFocus()){
            val imm = binding.nombreCompletoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.nombreCompletoTv.windowToken, 0)
        }
        if(binding.rfcTv.hasFocus()){
            val imm = binding.rfcTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.rfcTv.windowToken, 0)
        }
    }





}