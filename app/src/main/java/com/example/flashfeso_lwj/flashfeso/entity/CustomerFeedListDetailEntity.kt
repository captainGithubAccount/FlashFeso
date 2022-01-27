package com.example.flashfeso_lwj.flashfeso.entity


/*data class CustomerFeedListResponse(
    val code: Int,
    val data: CustomerFeedListEntitySummary,
    val msg: String
)

data class CustomerFeedListEntitySummary(
    val dataList: List<CustomerFeedListEntity>,
    val total: Int,
    val totalPage: Int
)*/

data class CustomerFeedListDetailEntity(
    val createTime: String?,
    val id: Int?,
    val leaveContent: String?,
    val picUrl: String?,
    val replyContent: String?,
    val replyTime: String?,
    val status: Int?,
    val updateTime: String?,
    val userId: Int?,
    val userName: String?,
    val userTel: String?,
    var isOpen: Boolean
)