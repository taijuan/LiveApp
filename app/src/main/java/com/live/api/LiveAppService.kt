package com.live.api

import androidx.lifecycle.LiveData
import com.live.base.model.BaseRes
import com.live.model.*
import com.live.utils.okHttpClient
import com.taijuan.retrofit.GsonConverterFactory
import com.taijuan.retrofit.LiveDataCallAdapterFactory
import com.taijuan.retrofit.SuccessError
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST


val liveAppApi: LiveAppService by lazy {
    Retrofit.Builder().baseUrl("http://47.52.147.78/sport-api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
        .callFactory(
            okHttpClient
        )
        .build()
        .create(LiveAppService::class.java)
}


interface LiveAppService {
    @POST("user/loginBypwd")
    fun logByPwd(@Body userReq: UserReq): LiveData<SuccessError<BaseRes<UserRes>>>

    @POST("sms/getForgetPwdCode")
    fun getForgetPwdCode(@Body regReq: RegReq): LiveData<SuccessError<BaseRes<Any>>>

    @POST("user/forgetPwdByCode")
    fun forgetPwdByCode(@Body regReq: RegReq): LiveData<SuccessError<BaseRes<Any>>>

    @POST("live/selectLivePage")
    fun selectLivePage(@Body req: LiveDataReq): LiveData<SuccessError<BaseRes<List<LiveDataRes>>>>
}