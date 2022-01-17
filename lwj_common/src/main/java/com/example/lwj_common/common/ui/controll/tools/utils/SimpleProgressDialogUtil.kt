/*
package com.example.lwj_common.common.ui.controll.tools.utils

import android.content.Context
import android.text.TextUtils
import com.example.lwj_common.common.ui.controll.dialog.SimpleProgressDialog

*/
/**
 *
 * 进度对话框工具类, x需要手动在活动销毁时给application中对应变量置空, 防止内存泄漏
 *//*

class SimpleProgressDialogUtil{

    var mDialog: SimpleProgressDialog? = null


    var mContext: Context? = null

    */
/**
     * 显示对话框
     *
     * @param context     Activity上下文
     * @param isCanCancel 是否可以按返回键取消
     *//*

    @Synchronized
    fun showHUD(context: Context, isCanCancel: Boolean) {
        showHUD(null, context, isCanCancel)
    }

    */
/**
     * 显示对话框
     *
     * @param progressMsg 提示文字
     * @param context     Activity上下文
     * @param isCanCancel 是否可以按返回键取消
     *//*

    @Synchronized
    fun showHUD(progressMsg: String?, context: Context, isCanCancel: Boolean) {
        try {
            if (mContext === context) {
                // 已经显示 只更新进度提示信息
                if (mDialog != null && !TextUtils.isEmpty(progressMsg)) {
                    mDialog!!.setProgressMsg(progressMsg)
                }
                return
            } else if (mContext != null && mDialog != null && mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
            mContext = context
            if (progressMsg == null) {
                // 没有提示信息的进度对话框
                mDialog = SimpleProgressDialog(mContext)
            } else {
                // 有提示信息的进度对话框
                mDialog = SimpleProgressDialog(mContext, progressMsg)
            }
            mDialog!!.setOnDismissListener { closeHUD() }
            mDialog!!.isCanCancel = isCanCancel
            mDialog!!.show()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    */
/**
     * 关闭对话框<br></br>
     * 对话框显示不正常时用此方法复位
     *//*

    @Synchronized
    fun closeHUD() {
        try {
            if (mContext != null) {
                mContext = null
            }
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
            if (mDialog != null) {
                mDialog = null
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}*/
