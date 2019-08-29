package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.next
import com.live.base.title
import com.live.utils.onClick
import com.live.utils.push
import kotlinx.android.synthetic.main.activity_create_live.*

class CreateLiveActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_live)
        topBar.title(R.string.create)
        topBar.back()
        topBar.next().onClick({
            push(LiveOptionsActivity::class.java)
        })
    }
}