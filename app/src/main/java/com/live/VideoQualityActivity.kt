package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.save
import com.live.base.title
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_video_quality.*

class VideoQualityActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_quality)
        topBar.title(R.string.video_quality)
        topBar.back()
        topBar.save().onClick({

        })
        btnHD.isSelected = true
        btnHD.onClick({
            btnHD.isSelected = true
            btnFHD.isSelected = false
        })
        btnFHD.onClick({
            btnFHD.isSelected = true
            btnHD.isSelected = false
        })

        btn30Fps.isSelected = true
        btn30Fps.onClick({
            btn30Fps.isSelected = true
            btn30Fps.isSelected = false
        })
        btn25Fps.onClick({
            btn25Fps.isSelected = true
            btn30Fps.isSelected = false
        })

        btnMedium.isSelected = true
        btnMedium.onClick({
            btnMedium.isSelected = true
            btnHigh.isSelected = false
            btnExcellent.isSelected = false
        })
        btnHigh.onClick({
            btnHigh.isSelected = true
            btnMedium.isSelected = false
            btnExcellent.isSelected = false
        })
        btnExcellent.onClick({
            btnExcellent.isSelected = true
            btnMedium.isSelected = false
            btnHigh.isSelected = false
        })

        btnEncodeFormat.isSelected = true
        btnEncodeFormat.onClick({
            btnEncodeFormat.isSelected = true
        })
    }
}