package com.example.lwj_common.common.ui.controll.tools.utils

import android.graphics.Bitmap
import android.os.Environment
import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import android.text.TextUtils
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.*
import java.lang.Exception

/**
 * 文件工具类
 */
class FileUtil {
    //在这里抽取了一个方法   可以封装到自己的工具类中...
    fun getFile(bitmap: Bitmap): File {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val file = File(Environment.getExternalStorageDirectory().toString() + "/temp.jpg")
        try {
            file.createNewFile()
            val fos = FileOutputStream(file)
            val `is`: InputStream = ByteArrayInputStream(baos.toByteArray())
            var x = 0
            val b = ByteArray(1024 * 100)
            while (`is`.read(b).also { x = it } != -1) {
                fos.write(b, 0, x)
            }
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }

    companion object {
        /**
         * 根据Uri返回文件绝对路径
         * 兼容了file:///开头的 和 content://开头的情况
         */
        fun getRealFilePathFromUri(context: Context, uri: Uri?): String? {
            if (null == uri) return null
            val scheme = uri.scheme
            var data: String? = null
            if (scheme == null) {
                data = uri.path
            } else if (ContentResolver.SCHEME_FILE.equals(scheme, ignoreCase = true)) {
                data = uri.path
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme, ignoreCase = true)) {
                val cursor = context.contentResolver.query(uri,
                    arrayOf(MediaStore.Images.ImageColumns.DATA),
                    null,
                    null,
                    null)
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                        if (index > -1) {
                            data = cursor.getString(index)
                        }
                    }
                    cursor.close()
                }
            }
            return data
        }

        /**
         * 检查文件是否存在
         * 注意File dir = new File(dirPath)创建的可能为文件或文件夹
         * dir.exist: 判断文件或文件夹是否存在
         * dir.isFile: 判断该File是否是文件(注意判断的是文件,而不是文件夹)
         * dir.mkdir(): 该文件或文件夹必须有上级目录才可以创建
         * dir.mkdirs(): 无论上级目录是否存在都可以创建一个文件或文件夹, 当上级目录不存在的时候会同时创建上级目录
         */
        fun checkDirPath(dirPath: String?): String? {
            if (TextUtils.isEmpty(dirPath)) {
                return ""
            }
            val dir = File(dirPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return dirPath
        }

        /*
     * bitmap转base64
     * */
        fun bitmapToBase64(bitmap: Bitmap?): String? {
            var result: String? = null
            var baos: ByteArrayOutputStream? = null
            try {
                if (bitmap != null) {
                    baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    baos.flush()
                    baos.close()
                    val bitmapBytes = baos.toByteArray()
                    result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    if (baos != null) {
                        baos.flush()
                        baos.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return result
        }

        /**
         * base64转为bitmap
         * @param base64Data
         * @return
         */
        fun base64ToBitmap(base64Data: String?): Bitmap {
            val bytes = Base64.decode(base64Data, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }
    }
}