package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.utils.onClick
import com.live.utils.pop
import com.live.utils.push
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        /**
         * 点击注册按钮
         */
        btnSignUp.onClick({
            push(HomeActivity::class.java)
        })
        /**
         * 点击登录按钮，跳转登录界面
         */
        btnSignIn.onClick({
            pop()
        })
    }
}