package com.rs.flashpeso.management

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ContentUris
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.ContactsContract
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.text.format.Formatter
import android.util.Log
import androidx.ads.identifier.AdvertisingIdClient
import androidx.ads.identifier.AdvertisingIdInfo
import androidx.core.app.ActivityCompat
import androidx.core.os.ConfigurationCompat
import com.example.lwj_common.common.managementUtils.AppInfoBean
import com.example.lwj_common.common.managementUtils.DeviceInfoBean
import com.example.lwj_common.common.managementUtils.PhoneInfoBean

import java.io.*
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.StringBuilder
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import java.util.regex.Pattern
import kotlin.Throws
import kotlin.jvm.Synchronized

object ManagementUtils {
    @JvmStatic
    fun getAppList(context: Context): List<AppInfoBean> {
        val appList: MutableList<AppInfoBean> = ArrayList()
        //getinstalledpackages()的方法获取到安装应用信息 ，他接收一个int flags的值，然后在网上查询资料全是 将Int  值 设置为0
        val packages = context.packageManager.getInstalledPackages(0)
        for (i in packages.indices) {
            val packageInfo = packages[i]
            // 判断是否为系统级应用， 若不是，展示出来
            if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                val appInfoBean = AppInfoBean()
                appInfoBean.appLabel = packageInfo.applicationInfo.loadLabel(
                    context.packageManager).toString() //app名字
                appInfoBean.appVersion = packageInfo.versionName
                appInfoBean.firstInstallTime = packageInfo.firstInstallTime
                appInfoBean.lastUpdateTime = packageInfo.lastUpdateTime
                appInfoBean.packageName = packageInfo.packageName //包名
                appInfoBean.versionCode = packageInfo.versionCode //版本号
                appInfoBean.versionName = packageInfo.versionName //版本名
                appInfoBean.toString()
                appList.add(appInfoBean)
                Log.e("TAG", appInfoBean.toString())
            }
        }
        return appList
    }

    //获取联系人列表
    @SuppressLint("Range")//这里要加, 否则报错
    @JvmStatic
    fun getContacts(context: Context): List<PhoneInfoBean> {
        val phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val NUM = ContactsContract.CommonDataKinds.Phone.NUMBER
        val TYPE = ContactsContract.CommonDataKinds.Phone.TYPE
        val LABEL = ContactsContract.CommonDataKinds.Phone.LABEL
        val ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        val phoneInfoBeanList: MutableList<PhoneInfoBean> = ArrayList()
        try {
            val cr = context.contentResolver
            val selection = "account_type != 'com.whatsapp'"
            val cursor = cr.query(phoneUri, null, selection, null, null)
            while (cursor!!.moveToNext()) {
                val phoneInfoBean = PhoneInfoBean()
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                getNameInfo(context, id, phoneInfoBean)
                getCompanyInfo(context, id, phoneInfoBean)
                phoneInfoBean.displayName = cursor.getString(cursor.getColumnIndex(NAME))
                phoneInfoBean.phoneValue = cursor.getString(cursor.getColumnIndex(NUM))
                phoneInfoBean.phoneLabel = ContactsContract.CommonDataKinds.Phone.getTypeLabel(
                    context.resources,
                    cursor.getInt(
                        cursor.getColumnIndex(TYPE)),
                    cursor.getString(cursor.getColumnIndex(LABEL))).toString()
                Log.e("TAG", phoneInfoBean.toString())
                phoneInfoBeanList.add(phoneInfoBean)
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return phoneInfoBeanList
    }

    //获取联系人姓名详细信息
    @SuppressLint("Range")
    fun getNameInfo(context: Context, contactId: Int, phoneInfoBean: PhoneInfoBean) {
        val contactUri =
            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId.toLong())
        val dataUri =
            Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Data.CONTENT_DIRECTORY)
        val nameCursor = context.contentResolver.query(
            dataUri,
            null,
            ContactsContract.Data.MIMETYPE + "=?",
            arrayOf(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE),
            null)
        while (nameCursor!!.moveToNext()) {
            val firstName =
                nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)) //名
            val lastName =
                nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)) //姓
            val middleName =
                nameCursor.getString(nameCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME)) //中间名
            //Log.e("TAG", firstName + "//" + lastName);
            phoneInfoBean.firstName = firstName
            phoneInfoBean.lastName = lastName
            phoneInfoBean.middleName = middleName
        }
        nameCursor.close()
    }

    @SuppressLint("Range")
    private fun getRawContactId(context: Context, contactId: Int): String? {
        val projection = arrayOf(ContactsContract.RawContacts._ID)
        val selection = ContactsContract.RawContacts.CONTACT_ID + "=?"
        val selectionArgs = arrayOf(contactId.toString() + "")
        val c = context.contentResolver.query(ContactsContract.RawContacts.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null)
            ?: return null
        var rawContactId = -1
        if (c.moveToFirst()) {
            rawContactId = c.getInt(c.getColumnIndex(ContactsContract.RawContacts._ID))
        }
        c.close()
        return rawContactId.toString()
    }

    //获取联系人公司信息
    @SuppressLint("Range")
    private fun getCompanyInfo(context: Context, contactId: Int, phoneInfoBean: PhoneInfoBean) {
        val rawContactId = getRawContactId(context, contactId)
        try {
            val orgWhere =
                ContactsContract.Data.RAW_CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?"
            val orgWhereParams = arrayOf(rawContactId,
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
            val cursor = context.contentResolver.query(ContactsContract.Data.CONTENT_URI,
                null, orgWhere, orgWhereParams, null) ?: return
            var name: String? = null
            var title: String? = null
            if (cursor.moveToFirst()) {
                name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY))
                title =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE))
                phoneInfoBean.companyName = name
                phoneInfoBean.jobTitle = title
            }
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getDeviceInfo(context: Context): DeviceInfoBean {
        val deviceInfoBean = DeviceInfoBean()
        deviceInfoBean.iMEI = getIMEI(context)
        deviceInfoBean.androidId = getAndroidID(context)
        deviceInfoBean.simSerialNumber = getSimSerialNumber(context)
        deviceInfoBean.uuid = getUUID(context)
        deviceInfoBean.generalDeviceId = getDeviceId(context)
        deviceInfoBean.ipAddress = getIpAddress(context) //需要权限android.permission.ACCESS_WIFI_STATE
        deviceInfoBean.macAddress = getMacAddress(context) //需要权限android.permission.ACCESS_WIFI_STATE 打开wifi才能获取到数据
        deviceInfoBean.networkType = getNetworkType(context) //需要权限android.permission.READ_PHONE_STATE android.permission.ACCESS_NETWORK_STATE
        deviceInfoBean.model = Build.MODEL
        deviceInfoBean.brand = Build.BRAND
        deviceInfoBean.idForAdvertising = getIdForAdvertising(context)
        deviceInfoBean.idForVendor = getVendorID(context)
        deviceInfoBean.isSimulator = isEmulator
        deviceInfoBean.androidVersion = Build.VERSION.RELEASE
        deviceInfoBean.totalMemory = getTotalMemory(context)
        deviceInfoBean.totalStorage = getTotalStorage(context)
        deviceInfoBean.rAM = getRAMInfo(context)
        deviceInfoBean.rOM = getROMInfo(context)
        deviceInfoBean.cpuCount = numCores
        deviceInfoBean.cpuSpeed = bogoMipsFromCpuInfo
        deviceInfoBean.displayResolution = getDisplayResolution(context)
        deviceInfoBean.carrierName = getCarrierName(context) //运营商名
        deviceInfoBean.country = getCountry(context)
        deviceInfoBean.deviceArch = deviceArch //CPU ARCHITECTURE
        return deviceInfoBean
    }

    val deviceArch: String?
        get() {
            var deviceArch: String? = null
            try {
                deviceArch = ""
                for (i in Build.SUPPORTED_ABIS.indices) {
                    deviceArch += if (i != Build.SUPPORTED_ABIS.size - 1) {
                        Build.SUPPORTED_ABIS[i].toString() + ","
                    } else {
                        Build.SUPPORTED_ABIS[i]
                    }
                }
            } catch (e: Exception) {
                deviceArch = Build.CPU_ABI
            }
            return deviceArch
        }

    fun getCountry(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        var country = tm.simCountryIso
        if (TextUtils.isEmpty(country)) {
            country = locale.country
        }
        if (TextUtils.isEmpty(country)) {
            country = ""
        }
        return country.toUpperCase()
    }

    val locale: Locale
        get() {
            val locale: Locale
            locale = try {
                val listCompat = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
                listCompat[0]
            } catch (e: Exception) {
                Locale.getDefault()
            }
            return locale
        }

    fun getCarrierName(context: Context): String {
        val manager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return manager.networkOperatorName
    }

    fun getDisplayResolution(context: Context): String {
        val dm = context.resources.displayMetrics
        val screenWidth = dm.widthPixels
        val screenHeight = dm.heightPixels
        return "$screenWidth*$screenHeight"
    }

    val isSDCardMount: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    /**
     * parse the CPU info to get the BogoMIPS.
     *
     * @return the BogoMIPS value as a String
     */
    val bogoMipsFromCpuInfo: String?
        get() {
            var result: String? = null
            val cpuInfo = readCPUinfo()
            Log.d("---couInfo", cpuInfo)
            val cpuInfoArray = cpuInfo.split( ":|\n".toRegex()).toTypedArray()

            Log.d("---size", cpuInfoArray.size.toString())


                Log.d("---", cpuInfoArray[0])

            for (i in cpuInfoArray.indices) {
                //cpuInfoArray[i]
                if (cpuInfoArray[i].toLowerCase(Locale.getDefault()).contains("bogomips")) {
                    result = cpuInfoArray[i + 1]
                    if (result == null) {
                        Log.d("---", "result is null")
                    } else {
                        Log.d("---", "result is not null")
                    }
                    break
                }
            }
            if (result != null) result = result.trim { it <= ' ' }
            return result
        }



    /**
     * @return the CPU info.
     * @see {https://stackoverflow.com/a/3021088/3014036}
     */
    fun readCPUinfo(): String {
        val cmd: ProcessBuilder
        var result = ""
        var `in`: InputStream? = null
        try {
            val args = arrayOf("/system/bin/cat", "/proc/cpuinfo")
            cmd = ProcessBuilder(*args)
            val process = cmd.start()
            `in` = process.inputStream
            val re = ByteArray(1024)
            while (`in`.read(re) != -1) {
                result = result + String(re)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        } finally {
            try {
                `in`?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }

    fun getTotalStorage(context: Context?): String? {
        return if (isSDCardMount) {
            val path = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            Formatter.formatFileSize(context,
                totalBlocks * blockSize + readSystem())
        } else {
            null
        }
    }

    fun readSystem(): Long {
        val root = Environment.getRootDirectory()
        val sf = StatFs(root.path)
        val blockSize = sf.blockSizeLong
        val blockCount = sf.blockCountLong
        return blockCount * blockSize
    }

    fun getROMInfo(context: Context?): String {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        return Formatter.formatFileSize(context, totalBlocks * blockSize)
    }

    fun getRAMInfo(context: Context): String {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = ActivityManager.MemoryInfo()
        manager.getMemoryInfo(info)
        val memory = info.availMem
        val totalMemory = info.totalMem
        return Formatter.formatFileSize(context, totalMemory)
    }

    fun getTotalMemory(context: Context?): String {
        return Formatter.formatFileSize(context, Runtime.getRuntime().totalMemory())
    }//Print exception

    //Default to return 1 core
//Get directory containing CPU info
    //Filter to only list the devices we care about
    //Return the number of cores (virtual CPU devices)
//Check if filename is "cpu", followed by a single digit number//Private Class to display only CPU devices in the directory listing
    //CPU个数
    val numCores: Int
        get() {
            val TAG = "CPU"

            //Private Class to display only CPU devices in the directory listing
            class CpuFilter : FileFilter {
                override fun accept(pathname: File): Boolean {
                    //Check if filename is "cpu", followed by a single digit number
                    return if (Pattern.matches("cpu[0-9]", pathname.name)) {
                        true
                    } else false
                }
            }
            return try {
                //Get directory containing CPU info
                val dir = File("/sys/devices/system/cpu/")
                //Filter to only list the devices we care about
                val files = dir.listFiles(CpuFilter())
                Log.d(TAG, "CPU Count: " + files.size)
                //Return the number of cores (virtual CPU devices)
                files.size
            } catch (e: Exception) {
                //Print exception
                Log.d(TAG, "CPU Count: Failed.")
                e.printStackTrace()
                //Default to return 1 core
                1
            }
        }

    fun getTotalMemory2(context: Context?): String {
        val str1 = "/proc/meminfo" // 系统内存信息文件
        val str2: String
        val arrayOfString: Array<String>
        var initial_memory: Long = 0
        try {
            val localFileReader = FileReader(str1)
            val localBufferedReader = BufferedReader(
                localFileReader, 8192)
            str2 = localBufferedReader.readLine() // 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+").toTypedArray()
            initial_memory = (Integer.valueOf(arrayOfString[1])
                .toInt() * 1024).toLong() // 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close()
        } catch (e: IOException) {
        }
        return Formatter.formatFileSize(context, initial_memory) // Byte转换为KB或者MB，内存大小规格化
    }

    val isEmulator: Boolean
        get() = (Build.FINGERPRINT.startsWith("generic")
                || Build.MODEL.contains("sdk_gphone")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)

    fun getVendorID(context: Context): Int {
        val manager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        val deviceList = manager.deviceList
        val deviceIterator: Iterator<UsbDevice> = deviceList.values.iterator()
        while (deviceIterator.hasNext()) {
            val device = deviceIterator.next()
            return device.vendorId
        }
        return 0
    }

    fun getIdForAdvertising(context: Context?): String? {
        var info: AdvertisingIdInfo? = null
        info = try {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context!!)
            adInfo.get()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return info?.id


    }

    fun getMacAddress(context: Context): String? {
        var macAddress: String? = null
        //如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            macAddress = getLocalMacAddressFromWifiInfo(context)
            if (!TextUtils.isEmpty(macAddress)) {
                return macAddress
            }
        } else {
            try {
                macAddress = getMacAddressFromIp(context)
                if (TextUtils.isEmpty(macAddress)) {
                    macAddress = machineHardwareAddress
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return macAddress
    }

    @SuppressLint("MissingPermission")
    fun getLocalMacAddressFromWifiInfo(context: Context): String {
        val wifi =
            context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val winfo = wifi.connectionInfo
        return winfo.macAddress
    }

    private const val INSTALLATION = "INSTALLATION"
    fun getUUID(context: Context): String? {
        var sID: String? = null
        if (sID == null) {
            val installation = File(context.filesDir, INSTALLATION)
            sID = try {
                if (!installation.exists()) writeInstallationFile(installation)
                readInstallationFile(installation)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        return sID
    }

    @Throws(IOException::class)
    private fun readInstallationFile(installation: File): String {
        val f = RandomAccessFile(installation, "r")
        val bytes = ByteArray(f.length().toInt())
        f.readFully(bytes)
        f.close()
        return String(bytes)
    }

    @Throws(IOException::class)
    private fun writeInstallationFile(installation: File) {
        val out = FileOutputStream(installation)
        val id = UUID.randomUUID().toString()
        out.write(id.toByteArray())
        out.close()
    }

    //1、刷机、root、恢复出厂设置等会使得 Android ID 改变；
    //2、Android 8.0之后，Android ID的规则发生了变化：
    //
    //对于升级到8.0之前安装的应用，ANDROID_ID会保持不变。如果卸载后重新安装的话，ANDROID_ID将会改变。
    //对于安装在8.0系统的应用来说，ANDROID_ID根据应用签名和用户的不同而不同。ANDROID_ID的唯一决定于应用签名、用户和设备三者的组合。
    //
    //两个规则导致的结果就是：
    //第一，如果用户安装APP设备是8.0以下，后来卸载了，升级到8.0之后又重装了应用，Android ID不一样；
    //第二，不同签名的APP，获取到的Android ID不一样。
    fun getAndroidID(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Settings.Secure.getString(context.contentResolver,
                Settings.Secure.ANDROID_ID)
        } else {
            Settings.System.getString(context.contentResolver,
                Settings.System.ANDROID_ID)
        }
    }

    fun getSimSerialNumber(context: Context): String? {
        return try {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            tm.simSerialNumber
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getDeviceId(context: Context): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return null
        }
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.deviceId
    }

    //获取手机IMEI
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMEI(context: Context): String? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return null
        }
        //6.0以上10.0以下需要READ_PHONE_STATE权限
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        try { //双卡手机
            val method = manager.javaClass.getMethod("getImei", Int::class.javaPrimitiveType)
            val imei1 = method.invoke(manager, 0) as String
            val imei2 = method.invoke(manager, 1) as String
            if (TextUtils.isEmpty(imei2) && !TextUtils.isEmpty(imei1)) {
                return imei1
            }
            if (!TextUtils.isEmpty(imei1)) {
                //因为手机卡插在不同位置，获取到的imei1和imei2值会交换，所以取它们的最小值,保证拿到的imei都是同一个
                var imei = ""
                imei = if (imei1.compareTo(imei2) <= 0) {
                    imei1
                } else {
                    imei2
                }
                return imei
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getMacAddressFromIp(context: Context): String {
        var mac_s = ""
        val buf = StringBuilder()
        try {
            val mac: ByteArray
            val ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getIpAddress(context)))
            mac = ne.hardwareAddress
            for (b in mac) {
                buf.append(String.format("%02X:", b))
            }
            if (buf.length > 0) {
                buf.deleteCharAt(buf.length - 1)
            }
            mac_s = buf.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mac_s
    }

    fun getIpAddress(context: Context): String? {
        try {
            val info =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            if (info != null && info.isConnected) {
                // 3/4g网络
                if (info.type == ConnectivityManager.TYPE_MOBILE) {
                    try {
                        val en = NetworkInterface.getNetworkInterfaces()
                        while (en.hasMoreElements()) {
                            val intf = en.nextElement()
                            val enumIpAddr = intf.inetAddresses
                            while (enumIpAddr.hasMoreElements()) {
                                val inetAddress = enumIpAddr.nextElement()
                                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                    return inetAddress.getHostAddress()
                                }
                            }
                        }
                    } catch (e: SocketException) {
                        e.printStackTrace()
                    }
                } else if (info.type == ConnectivityManager.TYPE_WIFI) {
                    //  wifi网络
                    val wifiManager =
                        context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                    val wifiInfo = wifiManager.connectionInfo
                    return intIP2StringIP(wifiInfo.ipAddress)
                } else if (info.type == ConnectivityManager.TYPE_ETHERNET) {
                    // 有限网络
                    return localIp
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun intIP2StringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }

    // 获取有限网IP
    private val localIp: String
        private get() {
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val intf = en.nextElement()
                    val enumIpAddr = intf.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            } catch (ex: SocketException) {
            }
            return "0.0.0.0"
        }

    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    val machineHardwareAddress: String?
        get() {
            var interfaces: Enumeration<NetworkInterface?>? = null
            try {
                interfaces = NetworkInterface.getNetworkInterfaces()
            } catch (e: SocketException) {
                e.printStackTrace()
            }
            var hardWareAddress: String? = null
            var iF: NetworkInterface? = null
            while (interfaces!!.hasMoreElements()) {
                iF = interfaces.nextElement()
                try {
                    hardWareAddress = bytesToString(iF!!.hardwareAddress)
                    if (hardWareAddress != null) break
                } catch (e: SocketException) {
                    e.printStackTrace()
                }
            }
            return hardWareAddress
        }

    /***
     * byte转为String
     * @param bytes
     * @return
     */
    private fun bytesToString(bytes: ByteArray?): String? {
        if (bytes == null || bytes.size == 0) {
            return null
        }
        val buf = StringBuilder()
        for (b in bytes) {
            buf.append(String.format("%02X:", b))
        }
        if (buf.length > 0) {
            buf.deleteCharAt(buf.length - 1)
        }
        return buf.toString()
    }

    val NETWORK_NONE: String? = null // 没有网络连接
    const val NETWORK_WIFI = "wifi" // wifi连接
    const val NETWORK_2G = "2g" // 2G
    const val NETWORK_3G = "3g" // 3G
    const val NETWORK_4G = "4g" // 4G
    const val NETWORK_5G = "5g" // 4G
    const val NETWORK_MOBILE = "mobile" // 手机流量

    /**
     * 获取运营商名字
     *
     * @param context context
     * @return int
     */
    fun getOperatorName(context: Context): String {
        /*
         * getSimOperatorName()就可以直接获取到运营商的名字
         * 也可以使用IMSI获取，getSimOperator()，然后根据返回值判断，例如"46000"为移动
         * IMSI相关链接：http://baike.baidu.com/item/imsi
         */
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        // getSimOperatorName就可以直接获取到运营商的名字
        return telephonyManager.simOperatorName
    }

    //获取当前网络连接的类型
    fun getNetworkType(context: Context): String? {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: // 为空则认为无网络
                return NETWORK_NONE // 获取网络服务
        // 获取网络类型，如果为空，返回无网络
        val activeNetInfo = connManager.activeNetworkInfo
        if (activeNetInfo == null || !activeNetInfo.isAvailable) {
            return NETWORK_NONE
        }
        // 判断是否为WIFI
        val wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (null != wifiInfo) {
            val state = wifiInfo.state
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI
                }
            }
        }
        // 若不是WIFI，则去判断是2G、3G、4G网
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }
        val networkType = telephonyManager.networkType
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_GSM -> NETWORK_2G
            TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_TD_SCDMA -> NETWORK_3G
            TelephonyManager.NETWORK_TYPE_LTE, TelephonyManager.NETWORK_TYPE_IWLAN, 19 -> NETWORK_4G
            TelephonyManager.NETWORK_TYPE_NR -> NETWORK_5G
            else -> NETWORK_MOBILE
        }
    }

    /**
     * 判断网络是否连接
     *
     * @param context context
     * @return true/false
     */
    fun isNetConnected(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.activeNetworkInfo
            if (info != null && info.isConnected) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 判断是否wifi连接
     *
     * @param context context
     * @return true/false
     */
    @Synchronized
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null) {
                val networkInfoType = networkInfo.type
                if (networkInfoType == ConnectivityManager.TYPE_WIFI || networkInfoType == ConnectivityManager.TYPE_ETHERNET) {
                    return networkInfo.isConnected
                }
            }
        }
        return false
    }
}