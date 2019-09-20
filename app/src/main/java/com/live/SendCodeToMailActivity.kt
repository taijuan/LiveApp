package com.live

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.model.RegReq
import com.live.utils.CountDownTimerUtils
import com.live.utils.isMobile
import com.live.utils.onClick
import com.live.utils.push
import com.live.viewmodel.ForgetPwdViewModel
import com.live.widget.showLoadingDialog
import kotlinx.android.synthetic.main.activity_send_code_to_mail.*

class SendCodeToMailActivity : BaseActivity() {
    private val forgetPwdViewModel: ForgetPwdViewModel by lazy {
        ForgetPwdViewModel(this)
    }

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
        btnNext.setChangeAlphaWhenDisable(true)
        btnNext.onClick({
            val req = RegReq(phone = etEmail.text.toString(), phoneCode = etCode.text.toString())
            push(ForgotPasswordActivity::class.java, Bundle().apply {
                putString("Forget", Gson().toJson(req))
            })
        })
        etEmail.doAfterTextChanged {
            val email = etEmail.text
            val code = etCode.text
            btnCode.isEnabled = !email.isNullOrEmpty() && isMobile(email)
            btnNext.isEnabled =
                !email.isNullOrEmpty() && isMobile(email) && !code.isNullOrEmpty() && code.length == 4
        }
        etCode.doAfterTextChanged {
            val email = etEmail.text
            val code = etCode.text
            btnNext.isEnabled =
                !email.isNullOrEmpty() && isMobile(email) && !code.isNullOrEmpty() && code.length == 4
        }
    }

    private fun sendCode() {
        val dialog = showLoadingDialog()
        shutDown()
        val req = RegReq(etEmail.text.toString())
        forgetPwdViewModel.getForgetPwdCode(req) {
            dialog.dismiss()
        }
    }

    private fun shutDown() {
        btnCode.isEnabled = false
        CountDownTimerUtils(this).apply {
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