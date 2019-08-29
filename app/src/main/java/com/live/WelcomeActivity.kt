package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.utils.pushAndPop


class WelcomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        window.decorView.postDelayed({
            pushAndPop(SignInActivity::class.java)
        }, 2000)
    }
}