package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.save
import com.live.base.title
import com.live.utils.loadImageCircleCrop
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        topBar.title(R.string.about)
        topBar.back()
        topBar.save().onClick({

        })
        imgHead.loadImageCircleCrop("http://pic39.nipic.com/20140321/18063302_210604412116_2.jpg")
    }
}