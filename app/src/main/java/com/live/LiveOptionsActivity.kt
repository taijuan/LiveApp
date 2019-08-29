package com.live

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.done
import com.live.base.title
import com.live.utils.onClick
import com.live.utils.push
import com.qmuiteam.qmui.layout.QMUIButton
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import kotlinx.android.synthetic.main.activity_live_options.*

class LiveOptionsActivity : BaseActivity() {
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
            push(CameraStyleActivity::class.java)
        })
        qmFloatStyle.addView(
            createItem(R.string.portrait),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
        qmFloatStyle.addView(
            createItem(R.string.front_camera),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
        /**
         * 视频质量设置
         */
        btnVideoQuality.setChangeAlphaWhenPress(true)
        btnVideoQuality.onClick({
            push(VideoQualityActivity::class.java)
        })

        qmFloatVideoQuality.addView(
            createItem(R.string.hd_1280_750),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
        qmFloatVideoQuality.addView(
            createItem(R.string._30_0fps_for_ntsc),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
        qmFloatVideoQuality.addView(
            createItem(R.string.high_1200kbps),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
        qmFloatVideoQuality.addView(
            createItem(R.string.h_264_baseline),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )

        /**
         * 音频质量设置
         */
        btnAudioQuality.setChangeAlphaWhenPress(true)
        btnAudioQuality.onClick({
            push(AudioQualityActivity::class.java)
        })

        qmFloatAudioQuality.addView(
            createItem(R.string.audio_rate_44100_0hz),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
        qmFloatAudioQuality.addView(
            createItem(R.string.audio_bitrate_medium_64kbps),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
        qmFloatAudioQuality.addView(
            createItem(R.string.audio_bit_16bit),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
        qmFloatAudioQuality.addView(
            createItem(R.string.audio_format_aac),
            ViewGroup.LayoutParams(-2, QMUIDisplayHelper.dp2px(this, 24))
        )
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
}