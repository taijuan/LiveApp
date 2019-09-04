package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        topBar.title(R.string.forgot_password)
        topBar.back()
        btnOK.setChangeAlphaWhenPress(true)
        btnOK.onClick({

        })
    }
}