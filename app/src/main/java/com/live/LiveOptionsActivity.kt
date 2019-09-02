package com.live

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.alivc.live.pusher.*
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.done
import com.live.base.title
import com.live.utils.logE
import com.live.utils.onClick
import com.live.utils.pushForResult
import com.qmuiteam.qmui.layout.QMUIButton
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import kotlinx.android.synthetic.main.activity_live_options.*

class LiveOptionsActivity : BaseActivity() {
    private var orientation: Int = AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT.ordinal
    private var cameraId = AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT.ordinal
    private var resolution = AlivcResolutionEnum.RESOLUTION_720P.ordinal
    private var qualityMode = AlivcQualityModeEnum.QM_FLUENCY_FIRST.ordinal
    private var videoEncodeMode = AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
    private var videoBeautyMode = AlivcBeautyLevelEnum.BEAUTY_Normal.ordinal
    private var audioRate = AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100.ordinal
    private var audioEncodeMode = AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
    private var audioChannel = AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal
    private var audioFormat = AlivcAudioAACProfileEnum.HE_AAC.ordinal
    private val layoutParams: ViewGroup.LayoutParams by lazy {
        ViewGroup.LayoutParams(
            -2,
            QMUIDisplayHelper.dp2px(this, 24)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_options)
        topBar.title(R.string.options)
        topBar.back()
        topBar.done().onClick({

        })
        /**
         * Camera Style
         */
        btnStyle.setChangeAlphaWhenPress(true)
        btnStyle.onClick({
            pushForResult(
                CameraStyleActivity::class.java,
                CAMERA_STYLE_REQUEST_CODE,
                Bundle().apply {
                    putInt(ORIENTATION_KEY, orientation)
                    putInt(CAMERA_ID, cameraId)
                })
        })
        intent?.setCameraStyle()
        /**
         * 视频质量设置
         */
        btnVideoQuality.setChangeAlphaWhenPress(true)
        btnVideoQuality.onClick({
            pushForResult(
                VideoQualityActivity::class.java,
                VIDEO_RESOLUTION_REQUEST_CODE,
                Bundle().apply {
                    putInt(RESOLUTION, resolution)
                    putInt(QUALITY_MODE, qualityMode)
                    putInt(VIDEO_ENCODE_MODE, videoEncodeMode)
                    putInt(VIDEO_BEAUTY_MODE, videoBeautyMode)
                })
        })
        intent?.setVideoStyle()

        /**
         * 音频质量设置
         */
        btnAudioQuality.setChangeAlphaWhenPress(true)
        btnAudioQuality.onClick({
            pushForResult(
                AudioQualityActivity::class.java,
                AUDIO_REQUEST_CODE,
                Bundle().apply {
                    putInt(AUDIO_RATE, audioRate)
                    putInt(AUDIO_FORMAT, audioFormat)
                    putInt(AUDIO_ENCODE_MODE, audioEncodeMode)
                    putInt(AUDIO_CHANNEL, audioChannel)
                })
        })
        intent?.setAudioStyle()
    }

    private fun createItem(res: Int): QMUIButton {
        val button = QMUIButton(this)
        button.minWidth = 0
        button.minHeight = 0
        button.minimumWidth = 0
        button.minimumHeight = 0
        val padding = QMUIDisplayHelper.dp2px(this, 12)
        button.setPadding(padding, 0, padding, 0)
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.backgroundStrongGray))
        button.radius = padding
        button.setTextColor(ContextCompat.getColor(this, R.color.textBlack))
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        button.gravity = Gravity.CENTER
        button.setText(res)
        button.typeface = ResourcesCompat.getFont(this, R.font.lato_regular)
        return button
    }

    private fun Intent.setCameraStyle() {
        qmFloatStyle.removeAllViews()
        orientation = this.getIntExtra(
            ORIENTATION_KEY,
            AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT.ordinal
        )
        when (orientation) {
            AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT.ordinal -> qmFloatStyle.addView(
                createItem(R.string.portrait),
                layoutParams
            )
            AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_LEFT.ordinal -> qmFloatStyle.addView(
                createItem(R.string.home_left),
                layoutParams
            )
            AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_RIGHT.ordinal -> qmFloatStyle.addView(
                createItem(R.string.home_right),
                layoutParams
            )
        }

        cameraId =
            this.getIntExtra(CAMERA_ID, AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT.cameraId)
        when (cameraId) {
            AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT.cameraId -> qmFloatStyle.addView(
                createItem(R.string.front_camera),
                layoutParams
            )
            AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK.cameraId -> qmFloatStyle.addView(
                createItem(R.string.back_camera),
                layoutParams
            )
        }
    }

    private fun Intent.setVideoStyle() {
        qmFloatVideoQuality.removeAllViews()
        resolution = this.getIntExtra(
            RESOLUTION,
            AlivcResolutionEnum.RESOLUTION_720P.ordinal
        )
        when (resolution) {
            AlivcResolutionEnum.RESOLUTION_540P.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.resolution_540p),
                layoutParams
            )
            AlivcResolutionEnum.RESOLUTION_720P.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.resolution_720p),
                layoutParams
            )
            AlivcResolutionEnum.RESOLUTION_1080P.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.resolution_1080p),
                layoutParams
            )
        }
        qualityMode = this.getIntExtra(
            QUALITY_MODE,
            AlivcQualityModeEnum.QM_FLUENCY_FIRST.ordinal
        )
        when (qualityMode) {
            AlivcQualityModeEnum.QM_FLUENCY_FIRST.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.quality_fluency_first),
                layoutParams
            )
            AlivcQualityModeEnum.QM_RESOLUTION_FIRST.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.quality_resolution_first),
                layoutParams
            )
        }

        videoEncodeMode = this.getIntExtra(
            VIDEO_ENCODE_MODE,
            AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
        )
        when (videoEncodeMode) {
            AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.video_encode_hard),
                layoutParams
            )
            AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.video_encode_soft),
                layoutParams
            )
        }

        videoBeautyMode = this.getIntExtra(
            VIDEO_BEAUTY_MODE,
            AlivcBeautyLevelEnum.BEAUTY_Normal.ordinal
        )
        when (videoBeautyMode) {
            AlivcBeautyLevelEnum.BEAUTY_Normal.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.video_beauty_normal),
                layoutParams
            )
            AlivcBeautyLevelEnum.BEAUTY_Professional.ordinal -> qmFloatVideoQuality.addView(
                createItem(R.string.video_beauty_professional),
                layoutParams
            )
        }
    }

    private fun Intent.setAudioStyle() {
        qmFloatAudioQuality.removeAllViews()
        audioRate =
            getIntExtra(AUDIO_RATE, AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100.ordinal)
        when (audioRate) {
            AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_32000.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_rate_32000_hz),
                layoutParams
            )
            AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_rate_44100_hz),
                layoutParams
            )
            AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_48000.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_rate_48000_hz),
                layoutParams
            )
        }

        audioEncodeMode =
            getIntExtra(AUDIO_ENCODE_MODE, AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal)
        when (audioEncodeMode) {
            AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_encode_soft),
                layoutParams
            )
            AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_encode_hard),
                layoutParams
            )
        }
        audioChannel =
            getIntExtra(AUDIO_CHANNEL, AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal)
        AlivcAudioChannelEnum.AUDIO_CHANNEL_ONE.ordinal.logE("AlivcAudioChannelEnum.AUDIO_CHANNEL_ONE.ordinal  ")
        AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal.logE("AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal  ")
        audioChannel.logE("audioChannel ")
        when (audioChannel) {
            AlivcAudioChannelEnum.AUDIO_CHANNEL_ONE.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_channel_mono),
                layoutParams
            )
            AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_channel_double),
                layoutParams
            )
        }
        audioFormat = getIntExtra(AUDIO_FORMAT, AlivcAudioAACProfileEnum.HE_AAC.ordinal)
        when (audioFormat) {
            AlivcAudioAACProfileEnum.AAC_LC.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_format_lc),
                layoutParams
            )
            AlivcAudioAACProfileEnum.HE_AAC.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_format_he),
                layoutParams
            )
            AlivcAudioAACProfileEnum.HE_AAC_v2.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_format_hev2),
                layoutParams
            )
            AlivcAudioAACProfileEnum.AAC_LD.ordinal -> qmFloatAudioQuality.addView(
                createItem(R.string.audio_format_ld),
                layoutParams
            )
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_STYLE_REQUEST_CODE) {
            data?.setCameraStyle()
            return
        }
        if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_RESOLUTION_REQUEST_CODE) {
            data?.setVideoStyle()
            return
        }
        if (resultCode == Activity.RESULT_OK && requestCode == AUDIO_REQUEST_CODE) {
            data?.setAudioStyle()
            return
        }
    }
}