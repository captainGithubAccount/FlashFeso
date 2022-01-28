package com.example.lwj_common.common.net
data class BaseArrayResponse<out T>(
    val total: Int,
    val totalPage: Int,
    val dataList: List<T>?

)

/*open class BaseArrayResponse2<T>(//或将open改为data
    val code: Int,
    val dataList: T,
    val msg: String?,
    val dataId: Int,
    val totalCount: Int

) {
    fun getResultState(): ResultState<T> {
        if(code == 200) {
            return ResultState.Success(msg, dataList)
        } else if(code == 4011) {
            return ResultState.Clear(msg)
        } else {
            return ResultState.Error(msg)
        }
    }
}*/

