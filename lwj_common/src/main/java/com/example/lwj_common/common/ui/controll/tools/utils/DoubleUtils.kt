package com.example.lwj_common.common.ui.controll.tools.utils

import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils.isNumber
import java.math.BigDecimal

object DoubleUtils {
    /**
     * 对double数据进行取精度.
     *
     * @param value        double数据.
     * @param scale        精度位数(保留的小数位数).
     * @param roundingMode 精度取值方式.
     * @return 精度计算后的数据.
     */
    fun round(
        value: Double, scale: Int,
        roundingMode: Int
    ): Double {
        var bd: BigDecimal? = BigDecimal(value)
        bd = bd!!.setScale(scale, roundingMode)
        val d = bd.toDouble()
        bd = null
        return d
    }

    /**
     * double 相加
     *
     * @param d1
     * @param d2
     * @return
     */
    fun sum(d1: Double, d2: Double): Double {
        val bd1 = BigDecimal(java.lang.Double.toString(d1))
        val bd2 = BigDecimal(java.lang.Double.toString(d2))
        return bd1.add(bd2).toDouble()
    }

    fun sum(d1: String?, d2: String?): Double {
        var d1 = d1
        var d2 = d2
        if (!isNumber(d1!!)) {
            d1 = "0"
        }
        if (!isNumber(d2!!)) {
            d2 = "0"
        }
        val bd1 = BigDecimal(d1)
        val bd2 = BigDecimal(d2)
        return bd1.add(bd2).toDouble()
    }

    fun sumToString(d1: Double, d2: Double): String {
        val bd1 = BigDecimal(java.lang.Double.toString(d1))
        val bd2 = BigDecimal(java.lang.Double.toString(d2))
        return java.lang.Double.toString(bd1.add(bd2).toDouble())
    }

    fun sumToString(d1: String?, d2: String?): String {
        var d1 = d1
        var d2 = d2
        if (!isNumber(d1!!)) {
            d1 = "0"
        }
        if (!isNumber(d2!!)) {
            d2 = "0"
        }
        val bd1 = BigDecimal(d1)
        val bd2 = BigDecimal(d2)
        return java.lang.Double.toString(bd1.add(bd2).toDouble())
    }

    /**
     * double 相减
     *
     * @param d1
     * @param d2
     * @return
     */
    fun sub(d1: Double, d2: Double): Double {
        val bd1 = BigDecimal(java.lang.Double.toString(d1))
        val bd2 = BigDecimal(java.lang.Double.toString(d2))
        return bd1.subtract(bd2).toDouble()
    }

    fun sub(d1: String?, d2: String?): Double {
        var d1 = d1
        var d2 = d2
        if (!isNumber(d1!!)) {
            d1 = "0"
        }
        if (!isNumber(d2!!)) {
            d2 = "0"
        }
        val bd1 = BigDecimal(d1)
        val bd2 = BigDecimal(d2)
        return bd1.subtract(bd2).toDouble()
    }

    fun subToString(d1: Double, d2: Double): String {
        val bd1 = BigDecimal(java.lang.Double.toString(d1))
        val bd2 = BigDecimal(java.lang.Double.toString(d2))
        return java.lang.Double.toString(bd1.subtract(bd2).toDouble())
    }

    fun subToString(d1: String?, d2: String?): String {
        var d1 = d1
        var d2 = d2
        if (!isNumber(d1!!)) {
            d1 = "0"
        }
        if (!isNumber(d2!!)) {
            d2 = "0"
        }
        val bd1 = BigDecimal(d1)
        val bd2 = BigDecimal(d2)
        return java.lang.Double.toString(bd1.subtract(bd2).toDouble())
    }

    /**
     * double 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    fun mul(d1: Double, d2: Double): Double {
        val bd1 = BigDecimal(java.lang.Double.toString(d1))
        val bd2 = BigDecimal(java.lang.Double.toString(d2))
        return bd1.multiply(bd2).toDouble()
    }

    fun mul(d1: String?, d2: String?): Double {
        if (!isNumber(d1!!)) {
            return 0.0
        }
        if (!isNumber(d2!!)) {
            return 0.0
        }
        val bd1 = BigDecimal(d1)
        val bd2 = BigDecimal(d2)
        return bd1.multiply(bd2).toDouble()
    }

    fun mulToString(d1: Double, d2: Double): String {
        val bd1 = BigDecimal(java.lang.Double.toString(d1))
        val bd2 = BigDecimal(java.lang.Double.toString(d2))
        return java.lang.Double.toString(bd1.multiply(bd2).toDouble())
    }

    fun mulToString(d1: String?, d2: String?): String {
        if (!isNumber(d1!!)) {
            return "0"
        }
        if (!isNumber(d2!!)) {
            return "0"
        }
        val bd1 = BigDecimal(d1)
        val bd2 = BigDecimal(d2)
        return java.lang.Double.toString(bd1.multiply(bd2).toDouble())
    }

    /**
     * double 除法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    fun div(d1: Double, d2: Double, scale: Int): Double {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        if (d1 == 0.0) {
            return 0.0
        }
        if (d2 == 0.0) {
            return 0.0
        }
        val bd1 = BigDecimal(java.lang.Double.toString(d1))
        val bd2 = BigDecimal(java.lang.Double.toString(d2))
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun div(d1: String?, d2: String?, scale: Int): Double {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        if (!isNumber(d1!!)) {
            return 0.0
        }
        if (!isNumber(d2!!)) {
            return 0.0
        }
        if (java.lang.Double.valueOf(d1) == 0.0) {
            return 0.0
        }
        if (java.lang.Double.valueOf(d2) == 0.0) {
            return 0.0
        }
        val bd1 = BigDecimal(d1)
        val bd2 = BigDecimal(d2)
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun divTOString(d1: Double, d2: Double, scale: Int): String {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        if (d1 == 0.0) {
            return "0"
        }
        if (d2 == 0.0) {
            return "0"
        }
        val bd1 = BigDecimal(java.lang.Double.toString(d1))
        val bd2 = BigDecimal(java.lang.Double.toString(d2))
        return java.lang.Double.toString(bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP)
            .toDouble())
    }

    /*
    * 更精准的除法运算
    * */
    fun divTOString(d1: String?, d2: String?, scale: Int): String {
        //  当然在此之前，你要判断分母是否为0，
        //  为0你可以根据实际需求做相应的处理
        if (!isNumber(d1!!)) {
            return "0"
        }
        if (!isNumber(d2!!)) {
            return "0"
        }
        if (java.lang.Double.valueOf(d1) == 0.0) {
            return "0"
        }
        if (java.lang.Double.valueOf(d2) == 0.0) {
            return "0"
        }
        val bd1 = BigDecimal(d1)
        val bd2 = BigDecimal(d2)
        return java.lang.Double.toString(bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).toDouble())
        //divide  @param: 除法计算的时候，括号里第一个参数是除数，第二个是精确小数位，第三个是舍入模式
    }
}