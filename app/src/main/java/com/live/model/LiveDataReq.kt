package com.live.model

import com.live.base.model.BaseReq

/**
 * {
"deviceType": "2",
"deviceUid": "string",
"limit": 10,
"page": 1,
"regIp": "127.0.0.1",
"userId": "string"
}
 */
data class LiveDataReq(
    val limit: Int = 10,
    var page: Int = 1,
    val regIp: String = "",
    val userId: String = ""
) : BaseReq()