package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.utils.exitApp
import com.live.utils.onClick
import com.live.utils.push
import com.live.viewmodel.clearUser
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        topBar.title(R.string.about)
        topBar.back()
        btnAccount.onClick({
            push(ProfileActivity::class.java)
        })
        btnChangePassword.onClick({
            push(ChangePasswordActivity::class.java)
        })
        btnOptions.onClick({
            push(LiveOptionsActivity::class.java)
        })
        btnLogOut.onClick({
            clearUser()
            exitApp()
        })
    }
}