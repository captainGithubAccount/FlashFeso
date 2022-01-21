package com.example.flashfeso_lwj.flashfeso.ui.controll.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.flashfeso_lwj.R
import com.example.flashfeso_lwj.flashfeso.base.DataResult
import com.example.flashfeso_lwj.base.ui.controll.activity.BasePageStyleActivity
import com.example.flashfeso_lwj.base.utils.SimpleProgressDialogUtil
import com.example.flashfeso_lwj.databinding.ActivityInformacionDeIdetidadBinding
import com.example.flashfeso_lwj.flashfeso.api.data.net.ImageFileUpload
import com.example.flashfeso_lwj.flashfeso.entity.UploadImageEntity
import com.example.flashfeso_lwj.flashfeso.ui.controll.dialog.CommonSelectDialog
import com.example.flashfeso_lwj.flashfeso.ui.controll.view.TakePhotoPopupWindow
import com.example.flashfeso_lwj.flashfeso.utils.back
import com.example.flashfeso_lwj.flashfeso.viewmodel.InformacionDeldetidadViewModel
import com.example.flashfeso_lwj.flashfeso.viewmodel.LoginViewModel
import com.example.lwj_base.common.base.BaseConstants
import com.example.lwj_common.common.camera.GlideEngine
import com.example.lwj_common.common.ui.controll.event.CommonItemOnclickListener
import com.example.lwj_common.common.ui.controll.tools.utils.FileUtil
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils
import com.google.gson.JsonSyntaxException
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.language.LanguageConfig
import com.yalantis.ucrop.view.OverlayView.FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject
import kotlin.collections.HashMap

@AndroidEntryPoint
class InformacionDeIdetidadActivity : BasePageStyleActivity<ActivityInformacionDeIdetidadBinding>() {
    @Inject
    @JvmField
    var mSimpleProgressDialogUtil: SimpleProgressDialogUtil? = null

    val mLoginViewModel: LoginViewModel by viewModels()

    private val FRENTA_DE_INE = 999
    private val DETRAS_DE_INE = 1000
    private val SELFIES_DE_TU_ROSTRO_COMPLETP = 1001

    private val SELECR_IMAGE_FRENTA_DE_INE = 99
    private val SELECR_IMAGE_DETRAS_DE_INE = 100
    private val SELECR_IMAGE_SELFIES_DE_TU_ROSTRO_COMPLETP = 101


    private val REQUEST_CODE_LIVENESS = 102
    private val REQUEST_CODE_MOBI_LIVENESS = 103


    private val viewModel: InformacionDeldetidadViewModel by viewModels()
    lateinit var mFirstCommonSelectDialog: CommonSelectDialog
    private var frentaDeIneUrl = ""
    private var detrasDeIneUrl = ""
    private var selfiesDeTuUrl = ""

    //是否走人脸识别 0-不 1-是
    private val flag = "0"
    private var orcFlag = ""

    override fun observe() {
        viewModel.liveData.observe(this, Observer {
            it.whenError {
                queryMobiRecord("001：Click the button on the front of the ID card!")
            }
        })

        viewModel.idCardLiveData.observe(this, Observer {
            mSimpleProgressDialogUtil?.closeHUD()
            it.whenSuccessResponse {
                Toast.makeText(this@InformacionDeIdetidadActivity, (it as DataResult.Success).successMessagle, Toast.LENGTH_SHORT).show()
                onBackPressed()
                mLoginViewModel.queryNotifyInicioBeanLiveData()
                startActivity(AgergarCuentaBancariaActivity::class.java)
            }
            it.whenError {

                Toast.makeText(this@InformacionDeIdetidadActivity, (it as DataResult.Error).errorMessage, Toast.LENGTH_SHORT).show()
            }
            it.whenClear {
                mLoginViewModel.queryNotifyUpdateLoginLiveData()
                Toast.makeText(this@InformacionDeIdetidadActivity, (it as DataResult.Clear).clearMessage, Toast.LENGTH_SHORT).show()
                onBackPressed()
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
            if (isClickUseful()) {
                hideKeyboardAll()
                //调接口打点,后台可以根据这个接口被调用来判断该用户目前在做什么来判断用户的操作
                queryMobiRecord("001：Click the button on the front of the ID card!")
                //底部弹出popupWindow用来打开相册, 选择图片等
                val takePhotoWindow = TakePhotoPopupWindow(this)
                takePhotoWindow.setListener(object : CommonItemOnclickListener {
                    override fun onItemOnclickListener(position: Int) {
                        if (position == TvPosition.TAKE_PHOTO.ordinal) {
                            openCameraTakePhoto(FRENTA_DE_INE)
                        } else if (position == TvPosition.SELECT_PICTURE.ordinal) {
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
            if (StringUtils.isEmpty(frentaDeIneUrl)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.favor_de_subir_una_foto_de_la_parte_frontal_de_su_identificacion_primero), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isClickUseful()) {
                queryMobiRecord("002：Click the button on the back of the ID card!")
                val takePhotoWindow: TakePhotoPopupWindow = TakePhotoPopupWindow(this)
                takePhotoWindow.setListener(object : CommonItemOnclickListener {
                    override fun onItemOnclickListener(position: Int) {
                        if (position == TvPosition.TAKE_PHOTO.ordinal) {
                            openCameraTakePhoto(DETRAS_DE_INE)
                        } else if (position == TvPosition.SELECT_PICTURE.ordinal) {
                            openGallerySelectImage(SELECR_IMAGE_DETRAS_DE_INE)
                        }
                        takePhotoWindow.dismiss()
                    }
                })
                takePhotoWindow.showInScreenBottom()
            }
        }

        binding.selfiesImg.setOnClickListener {
            hideKeyboardAll()
            if (StringUtils.isEmpty(frentaDeIneUrl)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.favor_de_subir_una_foto_de_la_parte_frontal_de_su_identificacion_primero), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (StringUtils.isEmpty(detrasDeIneUrl)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.favor_de_subir_una_foto_de_la_parte_trasera_de_su_identificacion_primero), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (isClickUseful()) {
                queryMobiRecord("003：Click the face recognition button!")
                if (StringUtils.equals(orcFlag, "Advance")) {
                    //todo doing- - - - - - - -- - - - -- - -- - - - -- - - - - - -- - --- - - -  - - - - -
                    //val intent = Intent(this@InformacionDeIdetidadActivity, LivenessActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE_LIVENESS)
                } else if (StringUtils.equals(orcFlag, "Mobi")) {
                    //val intent = Intent(this@InformacionDeIdetidadActivity, MobiLivenessActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE_MOBI_LIVENESS)
                } else {
                    openCameraTakePhoto(SELFIES_DE_TU_ROSTRO_COMPLETP)
                }
            }
        }

        binding.confirm.setOnClickListener {
            hideKeyboardAll()
            if (StringUtils.isEmpty(frentaDeIneUrl)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.favor_de_subir_una_foto_de_la_parte_frontal_de_su_identificacion), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (StringUtils.isEmpty(detrasDeIneUrl)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.favor_de_subir_una_foto_de_la_parte_trasera_de_su_identificacion), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (StringUtils.isEmpty(selfiesDeTuUrl)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.favor_de_tomarse_una_selfie), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val curp = binding.curpTv.text.toString().trim()
            if (!StringUtils.isIdCard(curp)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.rellene_el_numero_de_identificacion_completo), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apellidoPaterno: String = binding.apellidoPaternoTv.text.toString().trim()
            if (StringUtils.isEmpty(apellidoPaterno)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.rellene_el_apellido_paterno), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val apellidoMaterno: String = binding.apellidoMaternoTv.text.toString().trim()
            if (StringUtils.isEmpty(apellidoMaterno)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.rellene_el_apellido_materno), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombreCompleto: String = binding.nombreCompletoTv.text.toString().trim()
            if (StringUtils.isEmpty(nombreCompleto)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.rellene_el_nombre_completo), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rfc: String = binding.rfcTv.text.toString().trim()
            if (StringUtils.isEmpty(rfc)) {
                Toast.makeText(this@InformacionDeIdetidadActivity, resources.getString(R.string.introduzca_el_numero_de_impuesto_correcto), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isClickUseful()) {
                queryMobiRecord("004：Click the authentication button!")
                queryAuthIdCard(frentaDeIneUrl, detrasDeIneUrl, selfiesDeTuUrl, curp, apellidoPaterno, apellidoMaterno, nombreCompleto, rfc)
            }

        }
    }

    private fun queryAuthIdCard(
        topImage: String,
        downImage: String,
        selfImage: String,
        onlyCode: String,
        userFatherName: String,
        userMaterName: String,
        userName: String,
        taxNumber: String,
    ) {
        mSimpleProgressDialogUtil?.showHUD(this, false)
        val map: MutableMap<String, Any> = HashMap()
        map["onlyCode"] = onlyCode
        map["userFatherName"] = userFatherName
        map["userMaterName"] = userMaterName
        map["userName"] = userName
        map["taxNumber"] = taxNumber
        viewModel.queryIdCard(map as HashMap<String, Any>)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                FRENTA_DE_INE, SELECR_IMAGE_FRENTA_DE_INE -> {
                    pictureSelectResultHandle("front", data, binding.franteImg)
                }

                DETRAS_DE_INE, SELECR_IMAGE_DETRAS_DE_INE -> {
                    pictureSelectResultHandle("back", data, binding.detrasImg)
                }

                SELFIES_DE_TU_ROSTRO_COMPLETP -> {
                    pictureSelectResultHandle("self", data, binding.selfiesImg)
                }

                REQUEST_CODE_LIVENESS -> {
                    //doing - - - - - - - - - - - - - - - - -
                }

                REQUEST_CODE_MOBI_LIVENESS -> {
                    //doing - - - - - -  - - - - - - - - - - -
                }


            }
        }
    }

    private fun pictureSelectResultHandle(type: String, data: Intent?, view: ImageView) {
        //图片结果回掉
        val selectList: List<LocalMedia>? = PictureSelector.obtainMultipleResult(data)
        if (selectList != null && selectList.size > 0) {
            //由于是单选, 所以只获取到一张图片, 不需要遍历获取图片集
            val media = selectList[0]
            if (media.isCompressed) {
                val compressPath: String = media.compressPath
                //decodeFile(String pathName, Options opts)：从 pathName 指定的文件中解析、创建 Bitmap 对象。
                val bitmap: Bitmap = BitmapFactory.decodeFile(FileUtil.checkDirPath(compressPath))
                val file = File(FileUtil.checkDirPath(compressPath))
                uploadImage(type, compressPath, file, bitmap)
            } else {
                view.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_img_failed))
            }
        } else {
            view.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_img_failed))
        }
    }

    private fun uploadImage(type: String, fileName: String, file: File, bitmap: Bitmap) {
        mSimpleProgressDialogUtil?.showHUD(this, false)

        ImageFileUpload.uploadImage(type, fileName, file, object : ImageFileUpload.UploadImageCallBack {
            override fun onSuccess(data: UploadImageEntity, locationRequestParamValue: String) {
                mSimpleProgressDialogUtil?.closeHUD()
                try {
                    when (locationRequestParamValue) {
                        "front" -> {
                            if (!StringUtils.isEmpty(data.url)) {
                                runOnUiThread {
                                    frentaDeIneUrl = data.url!!
                                    binding.franteImg.setImageBitmap(bitmap)
                                    if (!StringUtils.isEmpty(data.orcFlag)) {
                                        orcFlag = data.orcFlag!!
                                    } else {
                                        orcFlag = ""
                                    }

                                    if (!StringUtils.isEmpty(data.idNumber)) {
                                        binding.curpTv.setText(data.idNumber)
                                    }

                                    if (!StringUtils.isEmpty(data.name)) {
                                        binding.nombreCompletoTv.setText(data.name)
                                    }

                                    if (!StringUtils.isEmpty(data.fatherLastName)) {
                                        binding.apellidoPaternoTv.setText(data.fatherLastName)
                                    }

                                    if (!StringUtils.isEmpty(data.motherLastName)) {
                                        binding.apellidoMaternoTv.setText(data.motherLastName)
                                    }
                                }
                            } else {
                                runOnUiThread {
                                    frentaDeIneUrl = ""
                                    binding.franteImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                                }
                            }
                        }

                        "back" -> {
                            if (!StringUtils.isEmpty(data.url)) {
                                runOnUiThread {
                                    detrasDeIneUrl = data.url!!
                                    binding.detrasImg.setImageBitmap(bitmap)
                                    if (StringUtils.isEmpty(data.orcFlag)) {
                                        orcFlag = data.orcFlag!!
                                    } else {
                                        orcFlag = ""
                                    }

                                    if (!StringUtils.isEmpty(data.fatherLastName)) {
                                        binding.apellidoPaternoTv.setText(data.fatherLastName)
                                    }

                                    if (!StringUtils.isEmpty(data.motherLastName)) {
                                        binding.apellidoMaternoTv.setText(data.motherLastName)
                                    }
                                }
                            } else {
                                runOnUiThread {
                                    detrasDeIneUrl = ""
                                    binding.detrasImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                                }
                            }
                        }

                        "self" -> {
                            if (!StringUtils.isEmpty(data.url)) {
                                runOnUiThread {
                                    binding.selfiesImg.setImageBitmap(bitmap)
                                    selfiesDeTuUrl = data.url!!
                                }
                            } else {
                                runOnUiThread {
                                    selfiesDeTuUrl = ""
                                    binding.selfiesImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                                }
                            }
                        }
                    }
                } catch (e: JsonSyntaxException) {
                    if (BaseConstants.ISLOG) Log.i("---JsonSyntaxException", "onUploadSuccess: ~~~~JSONException")
                    e.printStackTrace()
                    when (type) {
                        "front" -> runOnUiThread {
                            frentaDeIneUrl = ""
                            binding.franteImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                        }
                        "back" -> runOnUiThread {
                            detrasDeIneUrl = ""
                            binding.detrasImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                        }
                        "self" -> runOnUiThread {
                            selfiesDeTuUrl = ""
                            binding.selfiesImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                        }
                    }
                }
            }

            override fun onError(code: Int, info: String) {
                mSimpleProgressDialogUtil?.closeHUD()
                runOnUiThread {
                    if (code == 0x05 && !StringUtils.isEmpty(info)) {
                        Toast.makeText(this@InformacionDeIdetidadActivity, info, Toast.LENGTH_SHORT).show()
                    }
                }
                Log.i("---6666", "onUPloadFailure：~~~~ code: $code;  info$info")
                when (type) {
                    "front" -> runOnUiThread {
                        frentaDeIneUrl = ""
                        binding.franteImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                    }
                    "back" -> runOnUiThread {
                        detrasDeIneUrl = ""
                        binding.detrasImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                    }
                    "self" -> runOnUiThread {
                        selfiesDeTuUrl = ""
                        binding.selfiesImg.setImageDrawable(ContextCompat.getDrawable(this@InformacionDeIdetidadActivity, R.drawable.icon_img_failed))
                    }
                }

            }

        })
    }


    private fun openGallerySelectImage(requestCode: Int) {
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage()).selectionMode(PictureConfig.SINGLE).isEnableCrop(true).freeStyleCropMode(FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH).isPreviewImage(true).isCompress(true).isCamera(true)//显示or隐藏拍摄  false: 隐藏(仅仅当openGallery时候才有作用)
            .hideBottomControls(false).imageEngine(GlideEngine.createGlideEngine()).setLanguage(LanguageConfig.SPANISH).forResult(requestCode)
    }


    private fun openCameraTakePhoto(requestCode: Int) {
        PictureSelector.create(this).openCamera(PictureMimeType.ofImage()).selectionMode(PictureConfig.SINGLE).isEnableCrop(true).freeStyleCropMode(FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH).isPreviewImage(true).isCompress(true).hideBottomControls(false).imageEngine(GlideEngine.createGlideEngine()).setLanguage(LanguageConfig.SPANISH).forResult(requestCode)


    }

    enum class TvPosition {
        ZERO, TAKE_PHOTO, SELECT_PICTURE, CANCEL
    }

    private fun queryMobiRecord(event: String) {
        val map: MutableMap<String, Any> = HashMap()
        map["event"] = event
        viewModel.query(map as HashMap<String, Any>)

    }

    private fun hideKeyboardAll() {
        if (binding.curpTv.hasFocus()) {
            val imm = binding.curpTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.curpTv.windowToken, 0)
        }
        if (binding.apellidoPaternoTv.hasFocus()) {
            val imm = binding.apellidoPaternoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.apellidoPaternoTv.windowToken, 0)
        }
        if (binding.apellidoMaternoTv.hasFocus()) {
            val imm = binding.apellidoMaternoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.apellidoMaternoTv.windowToken, 0)
        }
        if (binding.nombreCompletoTv.hasFocus()) {
            val imm = binding.nombreCompletoTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.nombreCompletoTv.windowToken, 0)
        }
        if (binding.rfcTv.hasFocus()) {
            val imm = binding.rfcTv.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.rfcTv.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSimpleProgressDialogUtil = null
    }


}