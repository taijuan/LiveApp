package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.utils.CountDownTimerUtils
import com.live.utils.onClick
import com.live.utils.push
import kotlinx.android.synthetic.main.activity_send_code_to_mail.*

class SendCodeToMailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_code_to_mail)
        topBar.title(R.string.forgot_password)
        topBar.back()
        btnCode.setChangeAlphaWhenPress(true)
        btnCode.setChangeAlphaWhenDisable(true)
        btnCode.onClick({
            sendCode()
        })
        btnNext.setChangeAlphaWhenPress(true)
        btnNext.onClick({
            push(ForgotPasswordActivity::class.java)
        })
    }

    private fun sendCode() {
        shutDown()
    }

    private fun shutDown() {
        btnCode.isEnabled = false
        CountDownTimerUtils(lifecycle).apply {
            setOnTick {
                btnCode.text = String.format("%ds", it / 1000)
            }
            setOnFinish {
                btnCode.setText(R.string.verification_code)
                btnCode.isEnabled = true
            }
            start()
        }
    }
}