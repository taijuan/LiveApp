package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.fragment.onBackPressZxing
import com.live.fragment.requestZXing
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_open_network.*

class OpenNetworkActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_network)
        topBar.title(R.string.open_network)
        topBar.back()
        topBar.addRightImageButton(R.drawable.hk_scan, R.drawable.hk_scan).onClick({
            requestZXing(R.id.zxingController)
        })
        btnGo.setChangeAlphaWhenPress(true)
        btnGo.onClick({

        })
    }

    override fun onBackPressed() {
        if (onBackPressZxing()) {
            super.onBackPressed()
        }
    }
}