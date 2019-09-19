package com.live

import android.Manifest
import android.os.Bundle
import com.live.base.BaseActivity
import com.live.utils.logE
import com.live.utils.pushAndPop
import com.live.viewmodel.getUser
import com.taijuan.permission.request


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
            }, onDenied = { _, _ ->
                start()
            }, onNeverAskAgain = { _, _ ->
                start()
            }
        )
    }

    private fun start() {
        window.decorView.postDelayed({
            checkLoginState()
        }, 2000)
    }

    private fun checkLoginState() {
        val user = getUser()
        user.logE()
        if (user.id.isNullOrEmpty()) {
            pushAndPop(SignInActivity::class.java)
        } else {
            pushAndPop(HomeActivity::class.java)
        }
    }
}