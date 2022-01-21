package com.example.lwj_common.common.ui.controll.tools.ktx

import android.text.Editable
import android.text.InputFilter
import android.text.Selection
import android.text.TextWatcher
import android.widget.EditText

/**
 * 对输入的控件内容处理(常用来对输入的银行卡号进行每隔4位空格隔开)
 * */
fun EditText.toBankCardEditText(): EditText =
    this.apply {
        //设置输入长度不超过24位(包含空格)
        //this.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(24))
        this.addTextChangedListener(object : TextWatcher {
            var kongge = ' '

            //改变之前text长度
            var beforeTextLength = 0

            //改变之前的文字
            private val beforeChar: CharSequence? = null

            //改变之后text长度
            var onTextLength = 0

            //是否改变空格或光标
            var isChanged = false

            // 记录光标的位置
            var location = 0
            private lateinit var tempChar: CharArray
            private val buffer = StringBuffer()

            //已有空格数量
            var konggeNumberB = 0
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                beforeTextLength = s.length
                if (buffer.length > 0) {
                    buffer.delete(0, buffer.length)
                }
                konggeNumberB = 0
                for (i in 0 until s.length) {
                    if (s[i] == ' ') {
                        konggeNumberB++
                    }
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                onTextLength = s.length
                buffer.append(s.toString())
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false
                    return
                }
                isChanged = true
            }

            override fun afterTextChanged(s: Editable) {
                if (isChanged) {
                    location = this@apply.selectionEnd
                    var index = 0
                    while (index < buffer.length) {
                        if (buffer[index] == kongge) {
                            buffer.deleteCharAt(index)
                        } else {
                            index++
                        }
                    }
                    index = 0
                    var konggeNumberC = 0
                    while (index < buffer.length) {
                        if (index == 4 || index == 9 || index == 14 || index == 19) {
                            buffer.insert(index, kongge)
                            konggeNumberC++
                        }
                        index++
                    }
                    if (konggeNumberC > konggeNumberB) {
                        location += konggeNumberC - konggeNumberB
                    }
                    tempChar = CharArray(buffer.length)
                    buffer.getChars(0, buffer.length, tempChar, 0)
                    val str = buffer.toString()
                    if (location > str.length) {
                        location = str.length
                    } else if (location < 0) {
                        location = 0
                    }
                    this@apply.setText(str)
                    val etable = this@apply.text
                    Selection.setSelection(etable, location)
                    isChanged = false
                }
            }
        })
    }