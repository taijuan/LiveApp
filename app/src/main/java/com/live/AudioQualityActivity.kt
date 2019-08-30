package com.live

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alivc.live.pusher.AlivcAudioSampleRateEnum
import com.alivc.live.pusher.AlivcEncodeModeEnum
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.save
import com.live.base.title
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_audio_quality.*

const val AUDIO_RATE = "audio rate"
const val AUDIO_ENCODE_MODE = "audio encode mode"
const val AUDIO_REQUEST_CODE = 1003

class AudioQualityActivity : BaseActivity() {
    private var audioRate = AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100.ordinal
    private var audioEncodeMode = AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_quality)
        topBar.title(R.string.video_quality)
        topBar.back()
        topBar.save().onClick({
            setResult(
                Activity.RESULT_OK, Intent().putExtras(Bundle().apply {
                    putInt(AUDIO_RATE, audioRate)
                    putInt(AUDIO_ENCODE_MODE, audioEncodeMode)
                })
            )
            finish()
        })

        audioRate =
            intent.getIntExtra(AUDIO_RATE, AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100.ordinal)
        btnAudioRate32000.isSelected =
            audioRate == AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_32000.ordinal
        btnAudioRate44100.isSelected =
            audioRate == AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100.ordinal
        btnAudioRate48000.isSelected =
            audioRate == AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_48000.ordinal
        btnAudioRate32000.onClick({
            audioRate = AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_32000.ordinal
            btnAudioRate32000.isSelected = true
            btnAudioRate44100.isSelected = false
            btnAudioRate48000.isSelected = false
        })
        btnAudioRate44100.onClick({
            audioRate = AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100.ordinal
            btnAudioRate32000.isSelected = false
            btnAudioRate44100.isSelected = true
            btnAudioRate48000.isSelected = false
        })
        btnAudioRate48000.onClick({
            audioRate = AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_48000.ordinal
            btnAudioRate32000.isSelected = false
            btnAudioRate44100.isSelected = false
            btnAudioRate48000.isSelected = true
        })


        audioEncodeMode =
            intent.getIntExtra(AUDIO_ENCODE_MODE, AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal)
        btnAudioHardEncode.isSelected =
            audioEncodeMode == AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
        btnAudioSoftEncode.isSelected =
            audioEncodeMode == AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
        btnAudioHardEncode.onClick({
            audioEncodeMode = AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
            btnAudioHardEncode.isSelected = true
            btnAudioSoftEncode.isSelected = false
        })
        btnAudioSoftEncode.onClick({
            audioEncodeMode = AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
            btnAudioHardEncode.isSelected = false
            btnAudioSoftEncode.isSelected = true

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