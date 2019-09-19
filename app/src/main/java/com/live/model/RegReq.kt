package com.live.model

import com.live.base.model.BaseReq

data class RegReq(
    val phone: String,
    val phoneCode: String = "",
    val phonePwd: String = "",
    val regIp: String = ""
) : BaseReq()