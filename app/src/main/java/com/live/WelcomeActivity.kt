package com.live

import android.Manifest
import android.os.Bundle
import com.live.base.BaseActivity
import com.live.permission.request
import com.live.utils.pushAndPop


class WelcomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        request(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE, onGranted = {
                start()
            }, onDenied = {
                start()
            }
        )
    }

    private fun start() {
        window.decorView.postDelayed({
            pushAndPop(SignInActivity::class.java)
        }, 2000)
    }
}