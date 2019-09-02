package com.live.app

import android.app.Application
import androidx.annotation.Keep
import com.live.utils.initActivityManager
import com.live.utils.logE
import com.live.utils.setLogDebug

@Keep
lateinit var app: App

@Keep
class App : Application() {


    override fun onCreate() {
        app = this
        setLogDebug()
        initActivityManager()
        super.onCreate()
    }

    override fun onTrimMemory(level: Int) {
        /**
         * 内存状态变化 TRIM_MEMORY_UI_HIDDEN：表示应用在后台
         */
        "onTrimMemory: $level".logE()
        super.onTrimMemory(level)
    }

}