package com.live

import android.os.Bundle
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.save
import com.live.base.title
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_audio_quality.*

class AudioQualityActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_quality)
        topBar.title(R.string.video_quality)
        topBar.back()
        topBar.save().onClick({

        })

        btnAudioRate44100.isSelected = true
        btnAudioRate44100.onClick({
            btnAudioRate44100.isSelected = true
            btnAudioRate48000.isSelected = false
        })
        btnAudioRate48000.onClick({
            btnAudioRate48000.isSelected = true
            btnAudioRate44100.isSelected = false
        })

        btnAudioBitrateMedium.isSelected = true

        btnAudioBitrateMedium.onClick({
            btnAudioBitrateMedium.isSelected = true
            btnAudioBitrateHigh.isSelected = false
            btnAudioBitrateExcellent.isSelected = false
        })
        btnAudioBitrateHigh.onClick({
            btnAudioBitrateHigh.isSelected = true
            btnAudioBitrateMedium.isSelected = false
            btnAudioBitrateExcellent.isSelected = false
        })
        btnAudioBitrateExcellent.onClick({
            btnAudioBitrateExcellent.isSelected = true
            btnAudioBitrateMedium.isSelected = false
            btnAudioBitrateHigh.isSelected = false
        })
        btnBitDepth.isSelected = true
        btnBitDepth.onClick({
            btnBitDepth.isSelected = true
        })
        btnAudioFormat.isSelected = true
        btnAudioFormat.onClick({
            btnAudioFormat.isSelected = true
        })

    }
}