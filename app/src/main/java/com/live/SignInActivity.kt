package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.utils.onClick
import com.live.utils.push
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        /**
         * 点击登录按钮
         */
        btnSignIn.onClick({
            push(HomeActivity::class.java)
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