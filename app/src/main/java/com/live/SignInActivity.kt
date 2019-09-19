package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.model.UserReq
import com.live.utils.*
import com.live.viewmodel.UserViewModel
import com.live.widget.showLoadingDialog
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {
    private val signInViewModel: UserViewModel by lazy {
        UserViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        /**
         * 点击登录按钮
         */
        btnSignIn.onClick({
            val userName = etEmail.text.toString()
            val userPassword = etPassword.text.toString()
            if (userName.isEmpty() || !isMobile(userName)) {
                "请输入正确的email".showToast()
                return@onClick
            }
            if (userPassword.isEmpty() || !isPassword(userPassword)) {
                "请输入正确的password".showToast()
                return@onClick
            }
            val dialog = showLoadingDialog()
            val userReq = UserReq(userName, userPassword)
            signInViewModel.login(userReq, {
                push(HomeActivity::class.java)
                exitApp()
            }) {
                dialog.dismiss()
            }
        })
        /**
         * 点击注册按钮，跳至注册界面
         */
        btnSignUp.onClick({
            push(SignUpActivity::class.java)
        })

        btnForgotPassword.onClick({
            push(SendCodeToMailActivity::class.java)
        })
    }
}