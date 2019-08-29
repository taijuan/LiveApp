package com.live.model

import com.google.gson.annotations.SerializedName

data class LiveData(
    @SerializedName("imgUrl") val imgUrl: String = "http://pic25.nipic.com/20121112/9252150_150552938000_2.jpg",
    @SerializedName("title") val title: String = "Hyundai, S.Korea eye deal on low-cost carmaking venture",
    @SerializedName("qrCodeImgUrl") val qrCodeImgUrl: String = "",
    @SerializedName("event") val event: String = "Event",
    @SerializedName("session") val session: String = "Stream",
    @SerializedName("date") val date: String = "2019-7-20",
    @SerializedName("time") val time: String = "15:44-16:44"
)