package com.example.lwj_common.common.ui.controll.tools.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.lwj_common.common.ui.controll.tools.ktx.toTrim
import java.lang.Exception

object SystemServiceUtil {

    /**
     * 调用系统软键盘来隐藏软键盘
     * view  触发软键盘弹出的控件
     */
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 调用系统粘贴版实现文本复制
     * */
    fun copyTextToClipboard(context: Context, textSource: TextView){
        //获取ClipboardManager对象
        val clipboard: ClipboardManager = context.getSystemService(Context
            .CLIPBOARD_SERVICE) as ClipboardManager
        //把文本封装到ClipData中
        val clip = ClipData.newPlainText(null, textSource.toTrim())
        // Set the clipboard's primary clip.
        clipboard.setPrimaryClip(clip)
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     * @param phoneNum 电话号码
     */
    fun callPhone(phoneNum: String, activity: Activity) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            val data = Uri.parse("tel:$phoneNum")
            intent.data = data
            activity.startActivity(intent)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}