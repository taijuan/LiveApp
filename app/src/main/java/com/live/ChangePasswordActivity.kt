package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.utils.onClick
import com.live.utils.push
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        with(topBar) {
            title(R.string.change_password)
            back()
        }
        btnOK.setChangeAlphaWhenDisable(true)
        btnOK.setChangeAlphaWhenPress(true)
        btnOK.onClick({
            push(HomeActivity::class.java)
        })
    }
}