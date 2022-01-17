package com.example.lwj_common.common.ui.controll.tools.utils

import com.example.lwj_common.common.ui.controll.tools.utils.StringUtils.isEmpty
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author frank
 * @Description: 日期工具类
 * @date 2016/11/10
 */
object DateUtil {
    /**
     * 获取前n天日期、后n天日期
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    fun getOldDate(distanceDay: Int): String {
        val dft = SimpleDateFormat("yyyy-MM-dd")
        val beginDate = Date()
        val date = Calendar.getInstance()
        date.time = beginDate
        date[Calendar.DATE] = date[Calendar.DATE] + distanceDay
        var endDate: Date? = null
        try {
            endDate = dft.parse(dft.format(date.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dft.format(endDate)
    }

    /**
     * @param dates
     * @return
     */
    fun getDate(dates: Date?): String {
        val dft = SimpleDateFormat("yyyy-MM-dd")
        val date = Calendar.getInstance()
        date.time = dates
        date[Calendar.DATE] = date[Calendar.DATE]
        var endDate: Date? = null
        try {
            endDate = dft.parse(dft.format(date.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dft.format(endDate)
    }

    fun parseServerTime(serverTime: String?): Date? {
        val dft = SimpleDateFormat("yyyy-MM-dd")
        dft.timeZone = TimeZone.getTimeZone("GMT+8:00")
        var date: Date? = null
        try {
            date = dft.parse(serverTime)
        } catch (e: Exception) {
            //Timber.e(e, "");
        }
        return date
    }

    /**
     * 获取当前时间
     */
    val time: String
        get() {
            val simpleDateFormat = SimpleDateFormat("HH:mm")
            return simpleDateFormat.format(Date())
        }

    /**
     * 获取当前日期
     */
    val date: String
        get() {
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd")
            return simpleDateFormat.format(Date())
        }

    /**
     * 获取当前月份
     */
    val month: String
        get() {
            val simpleDateFormat = SimpleDateFormat("MM")
            return simpleDateFormat.format(Date())
        }

    /**
     * 获取当前年份
     */
    val year: String
        get() {
            val simpleDateFormat = SimpleDateFormat("yyyy")
            return simpleDateFormat.format(Date())
        }

    /**
     * 获取当前月份
     */
    val minute: String
        get() {
            val simpleDateFormat =
                SimpleDateFormat("HH:mm:ss")
            return simpleDateFormat.format(Date())
        }

    /**
     * 获取当前月份
     */
    val timeAll: String
        get() {
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return simpleDateFormat.format(Date())
        }

    /**
     * 获取n个月前的日期
     *
     * @param n 传入-n个月
     * @return
     */
    fun getMonthAgo(n: Int): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MONTH, n)
        return simpleDateFormat.format(calendar.time)
    }

    /*
     * 将时间戳转换为时间
     */
    fun stampToDate(timeMillis: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date(timeMillis * 1000)
        return simpleDateFormat.format(date)
    }

    fun getTimeMillis(time: String?): Long {
        //大写HH：24小时制，小写hh：12小时制
        //毫秒：SSS
        //指定转化前的格式
        if (isEmpty(time)) {
            return 0
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //转化后为Date日期格式
        var date: Date? = null
        try {
            date = sdf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        //Date转为时间戳long
        return date!!.time
    }

    fun getTimeMillisFor(time: String?): Long {
        //大写HH：24小时制，小写hh：12小时制
        //毫秒：SSS
        //指定转化前的格式
        if (isEmpty(time)) {
            return 0
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        //转化后为Date日期格式
        var date: Date? = null
        try {
            date = sdf.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        //Date转为时间戳long
        return date!!.time
    }

    fun getTimeMillisForString(time: String?): String {
        //大写HH：24小时制，小写hh：12小时制
        //毫秒：SSS
        //指定转化前的格式
        if (isEmpty(time)) {
            return "0"
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val df1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var str1 = ""
        try {
            val date = sdf.parse(time)
            str1 = df1.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str1
    }
}