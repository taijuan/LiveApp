package com.live.base.model

import com.google.gson.annotations.SerializedName

/**
 * 直接解析data为非数组对象
 */
data class BaseRes<T>(
    @SerializedName("ok") val ok: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: T,
    @SerializedName("msg") val msg: String
)