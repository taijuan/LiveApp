package com.live

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alivc.live.pusher.AlivcBeautyLevelEnum
import com.alivc.live.pusher.AlivcEncodeModeEnum
import com.alivc.live.pusher.AlivcQualityModeEnum
import com.alivc.live.pusher.AlivcResolutionEnum
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.save
import com.live.base.title
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_video_quality.*

const val RESOLUTION = "resolution"
const val QUALITY_MODE = "quality mode"
const val VIDEO_ENCODE_MODE = "video encode mode"
const val VIDEO_BEAUTY_MODE = "video beauty mode"
const val VIDEO_RESOLUTION_REQUEST_CODE = 1002

class VideoQualityActivity : BaseActivity() {
    private var resolution = AlivcResolutionEnum.RESOLUTION_1080P.ordinal
    private var qualityMode = AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
    private var videoEncodeMode = AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
    private var videoBeautyMode = AlivcBeautyLevelEnum.BEAUTY_Normal.ordinal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_quality)
        topBar.title(R.string.video_quality)
        topBar.back()
        topBar.save().onClick({
            setResult(
                Activity.RESULT_OK, Intent().putExtras(Bundle().apply {
                    putInt(RESOLUTION, resolution)
                    putInt(QUALITY_MODE, qualityMode)
                    putInt(VIDEO_ENCODE_MODE, videoEncodeMode)
                    putInt(VIDEO_BEAUTY_MODE, videoBeautyMode)
                })
            )
            finish()
        })
        /**
         * 分辨率
         */
        resolution = intent.getIntExtra(RESOLUTION, AlivcResolutionEnum.RESOLUTION_720P.ordinal)
        btnResolution540P.isSelected = resolution == AlivcResolutionEnum.RESOLUTION_540P.ordinal
        btnResolution720P.isSelected = resolution == AlivcResolutionEnum.RESOLUTION_720P.ordinal
        btnResolution1080P.isSelected = resolution == AlivcResolutionEnum.RESOLUTION_1080P.ordinal
        btnResolution540P.onClick({
            resolution = AlivcResolutionEnum.RESOLUTION_540P.ordinal
            btnResolution540P.isSelected = true
            btnResolution720P.isSelected = false
            btnResolution1080P.isSelected = false
        })
        btnResolution720P.onClick({
            resolution = AlivcResolutionEnum.RESOLUTION_720P.ordinal
            btnResolution540P.isSelected = false
            btnResolution720P.isSelected = true
            btnResolution1080P.isSelected = false
        })
        btnResolution1080P.onClick({
            resolution = AlivcResolutionEnum.RESOLUTION_1080P.ordinal
            btnResolution540P.isSelected = false
            btnResolution720P.isSelected = false
            btnResolution1080P.isSelected = true
        })

        /**
         * 视频播放质量模式
         */
        qualityMode =
            intent.getIntExtra(QUALITY_MODE, AlivcQualityModeEnum.QM_FLUENCY_FIRST.ordinal)
        btnFluencyFirst.isSelected = qualityMode == AlivcQualityModeEnum.QM_FLUENCY_FIRST.ordinal
        btnResolutionFirst.isSelected =
            qualityMode == AlivcQualityModeEnum.QM_RESOLUTION_FIRST.ordinal
        btnFluencyFirst.onClick({
            qualityMode = AlivcQualityModeEnum.QM_FLUENCY_FIRST.ordinal
            btnFluencyFirst.isSelected = true
            btnResolutionFirst.isSelected = false
        })
        btnResolutionFirst.onClick({
            qualityMode = AlivcQualityModeEnum.QM_RESOLUTION_FIRST.ordinal
            btnFluencyFirst.isSelected = false
            btnResolutionFirst.isSelected = true
        })

        /**
         * 视频编码模式
         */
        videoEncodeMode =
            intent.getIntExtra(VIDEO_ENCODE_MODE, AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal)
        btnVideoEncodeHard.isSelected =
            videoEncodeMode == AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
        btnVideoEncodeSoft.isSelected =
            videoEncodeMode == AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
        btnVideoEncodeHard.onClick({
            videoEncodeMode = AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal
            btnVideoEncodeHard.isSelected = true
            btnVideoEncodeSoft.isSelected = false
        })
        btnVideoEncodeSoft.onClick({
            videoEncodeMode = AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal
            btnVideoEncodeHard.isSelected = false
            btnVideoEncodeSoft.isSelected = true
        })


        videoBeautyMode =
            intent.getIntExtra(VIDEO_BEAUTY_MODE, AlivcBeautyLevelEnum.BEAUTY_Normal.ordinal)
        btnBeautynormal.isSelected = videoBeautyMode == AlivcBeautyLevelEnum.BEAUTY_Normal.ordinal
        btnBeautynormal.onClick({
            videoBeautyMode = AlivcBeautyLevelEnum.BEAUTY_Normal.ordinal
            btnBeautynormal.isSelected = true
            btnBeautyProfessional.isSelected = false
        })
        btnBeautyProfessional.onClick({
            videoBeautyMode = AlivcBeautyLevelEnum.BEAUTY_Professional.ordinal
            btnBeautynormal.isSelected = false
            btnBeautyProfessional.isSelected = true
        })
    }
}