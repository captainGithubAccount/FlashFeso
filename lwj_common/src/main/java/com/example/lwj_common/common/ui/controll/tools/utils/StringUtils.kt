package com.example.lwj_common.common.ui.controll.tools.utils

import java.util.regex.Pattern


object StringUtils{
    /**
     * 验证手机号码是否正确
     */
    fun isPhone(str: String?): Boolean {
        val pattern =
            Pattern.compile("^(\\+?52|0)?(2[234789]|3[12345789]|4[1-9]|5[5689]|6[1-9]|7[1-9]|8[1234679]|9[12356789])\\d{8}$")
        val m = pattern.matcher(str)
        return m.matches()
    }

    /**
     * 验证身份证号码
     */
    fun isIdCard(str: String?): Boolean {
        if (isEmpty(str)) {
            return false
        }
        val pattern =
            Pattern.compile("^[A-Z]{4}\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d{1}|3[01])(M|H)[A-Z]{5}[A-Z0-9]{2}$")
        val m = pattern.matcher(str)
        return m.matches()
    }

    /**
     * 判断是否为空
     */
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.length == 0 || str == "" || str.toString()
            .trim { it <= ' ' } == "" || str == "null"
    }

    /**
     * 比较两个String是否相等
     */
    fun equals(actual: String?, expected: String?): Boolean {
        return isEquals(actual, expected)
    }

    /**
     * 比较两个对象是否相等
     */
    fun isEquals(object1: Any?, object2: Any?): Boolean {
        return object1 === object2 || if (object1 == null) object2 == null else object1 == object2
    }

    /**
     * 判断邮箱是否合法
     */
    fun isEmail(email: String?): Boolean {
        if (null == email || "" == email) return false
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        val p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*") //复杂匹配
        val m = p.matcher(email)
        return m.matches()
    }

    /**
     * 判断是否是数字
     */
    fun isNumber(str: String): Boolean {
        if (isEmpty(str)) {
            return false
        }
        var re = true
        var point = 0
        for (i in 0 until str.length) {
            val ch = str[i]
            if (!Character.isDigit(ch) && !equals("-", ch.toString()) && !equals(".",
                    ch.toString())
            ) {
                re = false
                break
            }
            if (equals("-", ch.toString()) && i != 0) {
                re = false
                break
            }
            if (equals(".", ch.toString()) && i == 0) {
                re = false
                break
            }
            if (equals(".", ch.toString())) {
                point++
            }
        }
        if (point > 1) {
            re = false
        }
        return re
    }

}
