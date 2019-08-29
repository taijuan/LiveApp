package com.live.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

val okHttpClient: OkHttpClient by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .dns(HttpDns())
        .build()
    "OkHttp 初始化成功".logE()
    okHttpClient
}