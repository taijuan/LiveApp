package com.live

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alivc.live.pusher.AlivcAudioAACProfileEnum
import com.alivc.live.pusher.AlivcAudioChannelEnum
import com.alivc.live.pusher.AlivcAudioSampleRateEnum
import com.alivc.live.pusher.AlivcEncodeModeEnum
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.save
import com.live.base.title
import com.live.utils.logE
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_audio_quality.*

const val AUDIO_RATE = "audio rate"
const val AUDIO_ENCODE_MODE = "audio encode mode"
const val AUDIO_FORMAT = "audio format"
const val AUDIO_CHANNEL = "audio channel"
const val AUDIO_REQUEST_CODE = 1003

class AudioQualityActivity : BaseActivity() {
    private var audioRate = AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100.ordinal
    private var audioEncodeMode = AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
    private var audioFormat = AlivcAudioAACProfileEnum.HE_AAC.ordinal
    private var audioChannel = AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.channelCount
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
                    putInt(AUDIO_CHANNEL, audioChannel)
                    putInt(AUDIO_FORMAT, audioFormat)
                    audioChannel.logE("audioChannel ")
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
            audioEncodeMode == AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
        btnAudioSoftEncode.isSelected =
            audioEncodeMode == AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
        btnAudioHardEncode.onClick({
            audioEncodeMode = AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
            btnAudioHardEncode.isSelected = true
            btnAudioSoftEncode.isSelected = false
        })
        btnAudioSoftEncode.onClick({
            audioEncodeMode = AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
            btnAudioHardEncode.isSelected = false
            btnAudioSoftEncode.isSelected = true

        })

        audioChannel =
            intent.getIntExtra(AUDIO_CHANNEL, AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal)
        btnChannelMono.isSelected =
            audioChannel == AlivcAudioChannelEnum.AUDIO_CHANNEL_ONE.ordinal
        btnChannelDouble.isSelected =
            audioChannel == AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal
        btnChannelMono.onClick({
            audioChannel = AlivcAudioChannelEnum.AUDIO_CHANNEL_ONE.ordinal
            audioChannel.logE("audioChannel ")
            btnChannelMono.isSelected = true
            btnChannelDouble.isSelected = false
        })
        btnChannelDouble.onClick({
            audioChannel = AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal
            audioChannel.logE("audioChannel ")
            btnChannelMono.isSelected = false
            btnChannelDouble.isSelected = true
        })
        audioFormat = intent.getIntExtra(AUDIO_FORMAT, AlivcAudioAACProfileEnum.HE_AAC.ordinal)
        btnAudioFormatLC.isSelected = audioFormat == AlivcAudioAACProfileEnum.AAC_LC.ordinal
        btnAudioFormatHE.isSelected = audioFormat == AlivcAudioAACProfileEnum.HE_AAC.ordinal
        btnAudioFormatHEV2.isSelected = audioFormat == AlivcAudioAACProfileEnum.HE_AAC_v2.ordinal
        btnAudioFormatLD.isSelected = audioFormat == AlivcAudioAACProfileEnum.AAC_LD.ordinal
        btnAudioFormatLC.onClick({
            audioFormat = AlivcAudioAACProfileEnum.AAC_LC.ordinal
            btnAudioFormatLC.isSelected = true
            btnAudioFormatHE.isSelected = false
            btnAudioFormatHEV2.isSelected = false
            btnAudioFormatLD.isSelected = false
        })
        btnAudioFormatHE.onClick({
            audioFormat = AlivcAudioAACProfileEnum.HE_AAC.ordinal
            btnAudioFormatLC.isSelected = false
            btnAudioFormatHE.isSelected = true
            btnAudioFormatHEV2.isSelected = false
            btnAudioFormatLD.isSelected = false
        })
        btnAudioFormatHEV2.onClick({
            audioFormat = AlivcAudioAACProfileEnum.HE_AAC_v2.ordinal
            btnAudioFormatLC.isSelected = false
            btnAudioFormatHE.isSelected = false
            btnAudioFormatHEV2.isSelected = true
            btnAudioFormatLD.isSelected = false
        })
        btnAudioFormatLD.onClick({
            audioFormat = AlivcAudioAACProfileEnum.AAC_LD.ordinal
            btnAudioFormatLC.isSelected = false
            btnAudioFormatHE.isSelected = false
            btnAudioFormatHEV2.isSelected = false
            btnAudioFormatLD.isSelected = true
        })

    }
}