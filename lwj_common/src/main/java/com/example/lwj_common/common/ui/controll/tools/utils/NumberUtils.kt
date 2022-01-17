package com.example.lwj_common.common.ui.controll.tools.utils

import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils.isEmpty
import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils.isNumber
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @author frank
 * 数字处理相关
 */
object NumberUtils {
    /**
     * 字符串 转 double ，有异常，返回 0
     *
     * @param value 数字字符串
     * @return 数字
     */
    fun parseDouble(value: String): Double {
        if (isEmpty(value)) {
            return 0.0
        }
        try {
            return value.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return 0.0
    }

    /**
     * 字符串 转 int ，有异常，返回 0
     *
     * @param value 数字字符串
     * @return 数字
     */
    fun parseInt(value: String): Int {
        if (isEmpty(value)) {
            return 0
        }
        try {
            return value.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 字符串 转 float ，有异常，返回 0
     *
     * @param value 数字字符串
     * @return 数字
     */
    fun parseFloat(value: String): Float {
        if (isEmpty(value)) {
            return 0F
        }
        try {
            return value.toFloat()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return 0F
    }

    /**
     * 数字转字符串，精确到某一位，去除小数点后面不必要的 0
     */
    fun generalString(value: Int): String {
        return value.toString()
    }

    /**
     * 去除小数点后面不必要的 0
     */
    fun goToZeroString(value: String): String {
        var value = value
        return if (isNumber(value)) {
            if (value.indexOf(".") > 0) {
                value = value.replace("0+?$".toRegex(), "") //去掉多余的0
                value = value.replace("[.]$".toRegex(), "") //如最后一位是.则去掉
            }
            value
        } else {
            value
        }
    }

    fun goToZeroString(value: Double): String {
        var s = value.toString()
        if (s.indexOf(".") > 0) {
            s = s.replace("0+?$".toRegex(), "") //去掉多余的0
            s = s.replace("[.]$".toRegex(), "") //如最后一位是.则去掉
        }
        return s
    }

    /**
     * 数字转字符串，精确到某一位，去除小数点后面不必要的 0
     */
    fun generalString6(value: Double): String {
        return format6!!.format(value)
    }

    /**
     * 数字转字符串，精确到某一位，去除小数点后面不必要的 0
     */
    fun generalString6(value: String): String {
        return format6!!.format(parseDouble(value))
    }

    /**
     * 数字转字符串，精确到某一位，去除小数点后面不必要的 0
     */
    fun generalString5(value: Double): String {
        return format5!!.format(value)
    }

    /**
     * 数字转字符串，精确到某一位，去除小数点后面不必要的 0
     */
    fun generalString5(value: String): String {
        return format5!!.format(parseDouble(value))
    }

    /**
     * 数字转字符串，精确到某一位，去除小数点后面不必要的 0
     */
    fun generalString3(value: Double): String {
        return format3!!.format(value)
    }

    /**
     * 数字转字符串，精确到某一位，去除小数点后面不必要的 0
     */
    fun generalString3(value: String): String {
        return format3!!.format(parseDouble(value))
    }

    /**
     * 数字转字符串，精确到位，去除小数点后面不必要的 0
     */
    fun generalString2(value: Double): String {
        return format2!!.format(value)
    }

    /**
     * 数字转字符串，保留两位小数
     */
    fun saveDecimals2(value: String): String {
        return DecimalsFormat2()!!.format(parseDouble(value))
    }

    /**
     * 数字转字符串，精确到位，去除小数点后面不必要的 0
     */
    fun generalString2(value: String): String {
        return format2!!.format(parseDouble(value))
    }

    private var decimalFormat6: DecimalFormat? = null

    // 小数点后保留 6 位，不足不补 0
    private val format6: DecimalFormat?
        private get() {
            if (decimalFormat6 == null) {
                // 小数点后保留 6 位，不足不补 0
                decimalFormat6 = DecimalFormat("#.######")
            }
            return decimalFormat6
        }
    private var decimalFormat5: DecimalFormat? = null

    // 小数点后保留 5 位，不足不补 0
    private val format5: DecimalFormat?
        private get() {
            if (decimalFormat5 == null) {
                // 小数点后保留 5 位，不足不补 0
                decimalFormat5 = DecimalFormat("#.#####")
            }
            return decimalFormat5
        }
    private var decimalFormat3: DecimalFormat? = null

    // 小数点后保留 3 位，不足不补 0
    private val format3: DecimalFormat?
        private get() {
            if (decimalFormat3 == null) {
                // 小数点后保留 3 位，不足不补 0
                decimalFormat3 = DecimalFormat("#.###")
            }
            return decimalFormat3
        }
    private var decimalFormat2: DecimalFormat? = null

    // 小数点后保留 2 位，不足不补 0
    private val format2: DecimalFormat?
        private get() {
            if (decimalFormat2 == null) {
                // 小数点后保留 2 位，不足不补 0
                decimalFormat2 = DecimalFormat("#.##")
            }
            return decimalFormat2
        }
    private var decimalFormat4: DecimalFormat? = null
    private fun DecimalsFormat2(): DecimalFormat? {
        if (decimalFormat4 == null) {
            // 小数点后保留 2 位
            decimalFormat4 = DecimalFormat("0.00")
        }
        return decimalFormat4
    }

    /**
     * 保留两位小数的字符串相加
     *
     * @param val1 加数 1
     * @param val2 加数 2
     * @return 相加结果
     */
    fun add2(val1: String, val2: String): String {
        return addWithFormat(format2, val1, val2)
    }

    /**
     * 保留两位小数的字符串相乘
     */
    fun mul2(val1: String, val2: String): String {
        return mulWithFormat(format2, val1, val2)
    }

    /**
     * 保留两位小数的字符串相除
     */
    fun divide2(val1: String, val2: String): String {
        return divideWithFormat(format2, val1, val2)
    }

    /**
     * 保留三位小数的字符串相除
     */
    fun divide3(val1: String, val2: String): String {
        return divideWithFormat(format3, val1, val2)
    }

    /**
     * 保留两位小数的字符串相减
     */
    fun sub2(val1: String, val2: String): String {
        return subWithFormat(format3, val1, val2)
    }

    /**
     * 保留三位小数的字符串相加
     *
     * @param val1 加数 1
     * @param val2 加数 2
     * @return 相加结果
     */
    fun add3(val1: String, val2: String): String {
        return addWithFormat(format3, val1, val2)
    }

    /**
     * 保留三位小数的字符串相乘
     */
    fun mul3(val1: String, val2: String): String {
        return mulWithFormat(format3, val1, val2)
    }

    /**
     * 保留三位小数的字符串相减
     */
    fun sub3(val1: String, val2: String): String {
        return subWithFormat(format3, val1, val2)
    }

    private fun addWithFormat(format: DecimalFormat?, val1: String, val2: String): String {
        val decimal1: BigDecimal
        val decimal2: BigDecimal
        decimal1 = if (isEmpty(val1)) {
            BigDecimal(0)
        } else {
            try {
                BigDecimal(val1)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
        }
        decimal2 = if (isEmpty(val2)) {
            BigDecimal(0)
        } else {
            try {
                BigDecimal(val2)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
        }
        return format!!.format(decimal1.add(decimal2))
    }

    private fun subWithFormat(format: DecimalFormat?, val1: String, val2: String): String {
        val decimal1: BigDecimal
        val decimal2: BigDecimal
        decimal1 = if (isEmpty(val1)) {
            BigDecimal(0)
        } else {
            try {
                BigDecimal(val1)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
        }
        decimal2 = if (isEmpty(val2)) {
            BigDecimal(0)
        } else {
            try {
                BigDecimal(val2)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
        }
        return format!!.format(decimal1.subtract(decimal2))
    }

    private fun mulWithFormat(format: DecimalFormat?, val1: String, val2: String): String {
        val decimal1: BigDecimal
        val decimal2: BigDecimal
        decimal1 = if (isEmpty(val1)) {
            BigDecimal(0)
        } else {
            try {
                BigDecimal(val1)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
        }
        decimal2 = if (isEmpty(val2)) {
            BigDecimal(0)
        } else {
            try {
                BigDecimal(val2)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
        }
        return format!!.format(decimal1.multiply(decimal2))
    }

    private fun divideWithFormat(format: DecimalFormat?, val1: String, val2: String): String {
        val decimal1: BigDecimal
        val decimal2: BigDecimal
        decimal1 = if (isEmpty(val1)) {
            BigDecimal(0)
        } else {
            try {
                BigDecimal(val1)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
        }
        decimal2 = if (isEmpty(val2)) {
            BigDecimal(0)
        } else {
            try {
                BigDecimal(val2)
            } catch (e: NumberFormatException) {
                BigDecimal(0)
            }
        }
        return format!!.format(decimal1.divide(decimal2, 4, RoundingMode.HALF_UP))
    }

    /**
     * 返回格式化的银行号
     * xxxx xxxx xxxx xxx
     */
    fun formatBankNo(acctNo: String): String {
//        if (separator == 0) return sequence; // 以 0 个分隔
//        if (sequence == null) return "";
//        final int length = sequence.length();
//        if (length == 0 || separator == length) return sequence;
//        final StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            builder.append(sequence.charAt(i));
//            if ((i + 1) % separator == 0 && i != length - 1) {
//                builder.append(' ');
//            }
//        }
//        return builder.toString();
        return acctNo.substring(0, 4) + "****" + acctNo.substring(acctNo.length - 4)
    }

    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     *
     * @param str 需要处理的字符串
     * @return 处理完之后的字符串
     */
    fun addComma(str: String): String {
        val decimalFormat = DecimalFormat(",###")
        return decimalFormat.format(str.toDouble())
    }

    fun transformGe(str: String): String {

        //空的话返回空
        if (isEmpty(str)) {
            return ""
        }
        val replace = str.replace(",", "").replace("，", "")

        //如果不是数字返回原来的字符串
        if (!isNumber(replace)) {
            return str
        }
        val aDouble = java.lang.Double.valueOf(replace)
        return if (aDouble > 1.0 * 10000 * 10000 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            val v2 = v1 / 10000
            val v3 = v2 / 10000
            generalString2(v3) + "亿亿"
        } else if (aDouble > 1.0 * 10000 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            val v2 = v1 / 10000
            generalString2(v2) + "万亿"
        } else if (aDouble > 1.0 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            generalString2(v1) + "亿"
        } else if (aDouble > 1.0 * 10000) {
            val v = aDouble / 10000
            generalString2(v) + "万"
        } else {
            replace
        }
    }

    fun transformWan(str: String): String {

        //空的话返回空
        if (isEmpty(str)) {
            return ""
        }
        val replace = str.replace(",", "").replace("，", "")

        //如果不是数字返回原来的字符串
        if (!isNumber(replace)) {
            return str
        }
        val aDouble = java.lang.Double.valueOf(replace) * 10000
        return if (aDouble > 1.0 * 10000 * 10000 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            val v2 = v1 / 10000
            val v3 = v2 / 10000
            generalString2(v3) + "亿亿"
        } else if (aDouble > 1.0 * 10000 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            val v2 = v1 / 10000
            generalString2(v2) + "万亿"
        } else if (aDouble > 1.0 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            generalString2(v1) + "亿"
        } else if (aDouble > 1.0 * 10000) {
            val v = aDouble / 10000
            generalString2(v) + "万"
        } else {
            replace
        }
    }

    fun transformYi(str: String): String {

        //空的话返回空
        if (isEmpty(str)) {
            return ""
        }
        val replace = str.replace(",", "").replace("，", "")

        //如果不是数字返回原来的字符串
        if (!isNumber(replace)) {
            return str
        }
        val aDouble = java.lang.Double.valueOf(replace) * 10000 * 10000
        return if (aDouble > 1.0 * 10000 * 10000 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            val v2 = v1 / 10000
            val v3 = v2 / 10000
            generalString2(v3) + "亿亿"
        } else if (aDouble > 1.0 * 10000 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            val v2 = v1 / 10000
            generalString2(v2) + "万亿"
        } else if (aDouble > 1.0 * 10000 * 10000) {
            val v = aDouble / 10000
            val v1 = v / 10000
            generalString2(v1) + "亿"
        } else if (aDouble > 1.0 * 10000) {
            val v = aDouble / 10000
            generalString2(v) + "万"
        } else {
            replace
        }
    }
}