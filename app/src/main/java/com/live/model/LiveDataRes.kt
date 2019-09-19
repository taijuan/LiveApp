package com.live.model

data class LiveDataRes(
    val id: String,
    val anchor: String,
    val appName: String,
    val column: String,
    val coverImg: String,
    val createTime: String,
    val desc: String,
    val endTime: String,
    val liveStatus: Any,
    val playbackUrl: Any,
    val pullUrl: Any,
    val pushUrl: String,
    val streamName: String,
    val title: String
) {
    fun status(): LiveStatus = if (liveStatus is Map<*, *>) {
        val a = liveStatus["value"] as? Number
        when (a?.toInt()) {
            1 -> LiveStatus.LIVING
            2 -> LiveStatus.PLAYBACK
            else -> LiveStatus.PENDING
        }
    } else {
        LiveStatus.PENDING
    }

}
