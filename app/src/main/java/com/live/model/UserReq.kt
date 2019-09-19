package com.live.model

import com.live.base.model.BaseReq

data class UserReq(
    val phone: String,
    val phonePwd: String,
    val regIp: String =""
) : BaseReq()