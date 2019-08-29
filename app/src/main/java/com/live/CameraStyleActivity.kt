package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.save
import com.live.base.title
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_camera_style.*

class CameraStyleActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_style)
        topBar.title(R.string.camera)
        topBar.back()
        topBar.save().onClick({

        })
        tvLandscape.isSelected = true
        tvRearCamera.isSelected = true
        tvLandscape.onClick({
            tvLandscape.isSelected = true
            tvPortrait.isSelected = false
        })
        tvPortrait.onClick({
            tvPortrait.isSelected = true
            tvLandscape.isSelected = false
        })

        tvRearCamera.onClick({
            tvRearCamera.isSelected = true
            tvFrontCamera.isSelected = false
        })
        tvFrontCamera.onClick({
            tvFrontCamera.isSelected = true
            tvRearCamera.isSelected = false
        })
    }
}