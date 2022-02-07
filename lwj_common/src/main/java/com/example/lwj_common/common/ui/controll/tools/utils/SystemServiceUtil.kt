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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.lwj_common.R
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
    fun copyTextToClipboard(context: Context, textSource: String) { //获取ClipboardManager对象
        val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager //把文本封装到ClipData中
        val clip = ClipData.newPlainText(null, textSource.toTrim()) // Set the clipboard's primary clip.
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


    /**
     * 调用系统邮件软件   发送邮件
     * @param addresses 邮箱集合
     */
    private fun composeEmail(address: String, subjectContent: String, context: Context) {

        try {
            //注意: ACTION_SENDTO可以在模拟器或真机上正常使用, ACTION_SEND只有在真机上才可以正常使用, 模拟器上提示 没有应用程序可以执行此操作
            val intent = Intent(Intent.ACTION_SENDTO)

            intent.setData(Uri.parse("mailto:"))
            //收件人集合如: String[] address = {"1@abc.com", "2@abc.com"};
            //短信收件人, address为varag类型
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(address))

            intent.putExtra(Intent.EXTRA_SUBJECT, "Account：" + subjectContent) //设置短信主题
            if(intent.resolveActivity(context.packageManager) != null) { //能正常找到目标Activity
                //选择电子邮件客户端
                context.startActivity(Intent.createChooser(intent, "Seleccionar cliente de correo" + " " + "electrónico"))
            } else {
                //Toast.makeText(this, getResources().getString(R.string .software_relacionado_no_encontrado), Toast.LENGTH_SHORT).show()
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}