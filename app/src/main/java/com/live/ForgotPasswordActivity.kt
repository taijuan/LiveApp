package com.live

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import com.google.gson.Gson
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.model.RegReq
import com.live.utils.*
import com.live.viewmodel.ForgetPwdViewModel
import com.live.widget.showLoadingDialog
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity() {
    private val req: RegReq by lazy {
        Gson().fromJson(intent.getStringExtra("Forget"), RegReq::class.java)
    }
    private val forgetPwdViewModel: ForgetPwdViewModel by lazy {
        ForgetPwdViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        topBar.title(R.string.forgot_password)
        topBar.back()
        btnOK.setChangeAlphaWhenPress(true)
        btnOK.setChangeAlphaWhenDisable(true)
        btnOK.onClick({
            val newPwd = etNewPassword.text.toString()
            val confirmPwd = etConfirmPassword.text.toString()
            if (newPwd != confirmPwd) {
                "密码不一致".showToast()
                return@onClick
            }
            val dialog = showLoadingDialog()
            forgetPwdViewModel.forgetPwdByCode(req.copy(phonePwd = etNewPassword.text.toString()), {
                push(SignInActivity::class.java)
                exitApp()
            }) {
                dialog.dismiss()
            }
        })
        etNewPassword.doAfterTextChanged {
            checkPassword()
        }
        etConfirmPassword.doAfterTextChanged {
            checkPassword()
        }
    }

    private fun checkPassword() {
        val newPwd = etNewPassword.text.toString()
        val confirmPwd = etConfirmPassword.text.toString()
        btnOK.isEnabled =
            !newPwd.isNullOrEmpty() && isPassword(newPwd) &&
                    !confirmPwd.isNullOrEmpty() && isPassword(confirmPwd)
    }
}