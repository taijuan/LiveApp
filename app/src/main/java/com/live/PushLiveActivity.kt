package com.live

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.view.View
import com.alivc.component.custom.AlivcLivePushCustomDetect
import com.alivc.component.custom.AlivcLivePushCustomFilter
import com.alivc.live.detect.TaoFaceFilter
import com.alivc.live.filter.TaoBeautyFilter
import com.alivc.live.pusher.*
import com.live.app.app
import com.live.base.BaseActivity
import com.live.utils.logE
import com.live.utils.logT
import com.live.utils.onClick
import com.live.utils.pop
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_push_live.*

class PushLiveActivity : BaseActivity(), SurfaceHolder.Callback, Runnable,
    AlivcLivePushNetworkListener {

    private val handler = Handler()
    private val pusher: AlivcLivePusher = AlivcLivePusher()
    private var isFlash = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOrientation()
        setContentView(R.layout.activity_push_live)
        btnBack.onClick({
            pop()
        })
        btnFlash.onClick({
            isFlash = !isFlash
            pusher.setFlash(isFlash)
        })
        pusher.init(this, getPushConfig())
        pusher.setLivePushNetworkListener(this)
        pushView.holder.addCallback(this)
        btnCameraSwitch.onClick({
            try {
                pusher.switchCamera()
            } catch (e: Exception) {
                e.logT()
            }
        })
        btnStartPush.onClick({
            try {
                pusher.startPushAysnc("rtmp://push.cdhker.com/test0806/test06?auth_key=1565082602000-0-0-1bf369caf6b774c961359ce39a61cca7")
            } catch (e: Exception) {
                e.logT()
                try {
                    pusher.restartPushAync()
                } catch (e: Exception) {
                    e.logT()
                }
            }
        }, 1000)
        btnStopPush.onClick({
            try {
                pusher.stopPush()
            } catch (e: Exception) {
                e.logT()
            }
        }, 1000)

        pusher.setCustomDetect(object : AlivcLivePushCustomDetect {
            private val taoFaceFilter: TaoFaceFilter = TaoFaceFilter(app)
            override fun customDetectCreate() {
                taoFaceFilter.customDetectCreate()
            }

            override fun customDetectProcess(
                data: Long,
                width: Int,
                height: Int,
                rotation: Int,
                format: Int,
                extra: Long
            ): Long {
                return taoFaceFilter.customDetectProcess(
                    data,
                    width,
                    height,
                    rotation,
                    format,
                    extra
                )

            }

            override fun customDetectDestroy() {
                taoFaceFilter.customDetectDestroy()
            }
        })

        pusher.setCustomFilter(object : AlivcLivePushCustomFilter {
            private val taoBeautyFilter = TaoBeautyFilter()
            override fun customFilterCreate() {
                taoBeautyFilter.customFilterCreate()
            }

            override fun customFilterUpdateParam(
                fSkinSmooth: Float,
                fWhiten: Float,
                fWholeFacePink: Float,
                fThinFaceHorizontal: Float,
                fCheekPink: Float,
                fShortenFaceVertical: Float,
                fBigEye: Float
            ) {
                taoBeautyFilter.customFilterUpdateParam(
                    fSkinSmooth,
                    fWhiten,
                    fWholeFacePink,
                    fThinFaceHorizontal,
                    fCheekPink,
                    fShortenFaceVertical,
                    fBigEye
                )
            }

            override fun customFilterSwitch(on: Boolean) {
                taoBeautyFilter.customFilterSwitch(on)
            }

            override fun customFilterProcess(
                inputTexture: Int,
                textureWidth: Int,
                textureHeight: Int,
                extra: Long
            ): Int {
                return taoBeautyFilter.customFilterProcess(
                    inputTexture,
                    textureWidth,
                    textureHeight,
                    extra
                )
            }

            override fun customFilterDestroy() {
                taoBeautyFilter.customFilterDestroy()
            }
        })
    }

    private fun getPushConfig() = AlivcLivePushConfig().apply {
        isEnableBitrateControl = true
        setAutoFocus(true)
        requestedOrientation = when (intent.getIntExtra(
            ORIENTATION_KEY,
            AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT.ordinal
        )) {
            AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_RIGHT.ordinal -> {
                setPreviewOrientation(AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_RIGHT)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
            AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_LEFT.ordinal -> {
                setPreviewOrientation(AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_LEFT)
                ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
            }
            else -> {
                setPreviewOrientation(AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT)
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
        previewDisplayMode = AlivcPreviewDisplayMode.ALIVC_LIVE_PUSHER_PREVIEW_ASPECT_FILL
        when (intent.getIntExtra(CAMERA_ID, AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK.ordinal)) {
            AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK.ordinal -> setCameraType(
                AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK
            )
            else -> setCameraType(AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT)
        }
        when (intent.getIntExtra(RESOLUTION, AlivcResolutionEnum.RESOLUTION_SELFDEFINE.ordinal)) {
            AlivcResolutionEnum.RESOLUTION_540P.ordinal -> setResolution(AlivcResolutionEnum.RESOLUTION_540P)
            AlivcResolutionEnum.RESOLUTION_720P.ordinal -> setResolution(AlivcResolutionEnum.RESOLUTION_720P)
            AlivcResolutionEnum.RESOLUTION_1080P.ordinal -> setResolution(AlivcResolutionEnum.RESOLUTION_720P)
            else -> setResolution(AlivcResolutionEnum.RESOLUTION_SELFDEFINE)

        }
        qualityMode = when (intent.getIntExtra(
            QUALITY_MODE,
            AlivcQualityModeEnum.QM_RESOLUTION_FIRST.ordinal
        )) {
            AlivcQualityModeEnum.QM_RESOLUTION_FIRST.ordinal -> AlivcQualityModeEnum.QM_CUSTOM
            else -> AlivcQualityModeEnum.QM_RESOLUTION_FIRST
        }
        when (intent.getIntExtra(VIDEO_ENCODE_MODE, AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal)) {
            AlivcEncodeModeEnum.Encode_MODE_HARD.ordinal -> setVideoEncodeMode(AlivcEncodeModeEnum.Encode_MODE_HARD)
            else -> setVideoEncodeMode(AlivcEncodeModeEnum.Encode_MODE_SOFT)
        }
        beautyLevel = when (intent.getIntExtra(
            VIDEO_BEAUTY_MODE,
            AlivcBeautyLevelEnum.BEAUTY_Normal.ordinal
        )) {
            AlivcBeautyLevelEnum.BEAUTY_Professional.ordinal -> AlivcBeautyLevelEnum.BEAUTY_Professional
            else -> AlivcBeautyLevelEnum.BEAUTY_Normal
        }
        when (intent.getIntExtra(
            AUDIO_RATE,
            AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_48000.ordinal
        )) {
            AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_48000.ordinal -> setAudioSamepleRate(
                AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_48000
            )
            AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_32000.ordinal -> setAudioSamepleRate(
                AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_32000
            )
            else -> setAudioSamepleRate(
                AlivcAudioSampleRateEnum.AUDIO_SAMPLE_RATE_44100
            )
        }
        when (intent.getIntExtra(AUDIO_ENCODE_MODE, AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal)) {
            AlivcEncodeModeEnum.Encode_MODE_SOFT.ordinal -> setAudioEncodeMode(AlivcEncodeModeEnum.Encode_MODE_SOFT)
            else -> setAudioEncodeMode(AlivcEncodeModeEnum.Encode_MODE_HARD)
        }
        when (intent.getIntExtra(AUDIO_CHANNEL, AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal)) {
            AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO.ordinal -> setAudioChannels(
                AlivcAudioChannelEnum.AUDIO_CHANNEL_TWO
            )
            else -> setAudioChannels(AlivcAudioChannelEnum.AUDIO_CHANNEL_ONE)
        }
        audioProfile =
            when (intent.getIntExtra(AUDIO_FORMAT, AlivcAudioAACProfileEnum.HE_AAC.ordinal)) {
                AlivcAudioAACProfileEnum.HE_AAC.ordinal -> AlivcAudioAACProfileEnum.HE_AAC
                AlivcAudioAACProfileEnum.HE_AAC_v2.ordinal -> AlivcAudioAACProfileEnum.HE_AAC_v2
                AlivcAudioAACProfileEnum.AAC_LD.ordinal -> AlivcAudioAACProfileEnum.AAC_LD
                else -> AlivcAudioAACProfileEnum.AAC_LC
            }
        setAutoFocus(true)
        this.setConnectRetryCount(10)
    }

    private fun setOrientation() {

    }


    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        pushView.holder.removeCallback(this)
        try {
            pusher.stopPush()
        } catch (e: Exception) {
            e.logT()
        }
        try {
            pusher.stopPreview()
        } catch (e: Exception) {
            e.logT()
        }
        try {
            pusher.destroy()
        } catch (e: Exception) {
            e.logT()
        }
        super.onDestroy()
    }

    /**
     * surfaceView holder回调
     */
    override fun surfaceChanged(surfaceHolder: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        "surfaceChanged".logE()
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder?) {
        "surfaceDestroyed".logE()
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
        "surfaceCreated".logE()
        try {
            if (pusher.currentStatus == AlivcLivePushStats.INIT) {
                pusher.startPreviewAysnc(pushView)
                checkPushStatusWhenSurfaceCreated()
            } else {
                pusher.pause()
                pusher.resumeAsync()
            }
        } catch (e: Exception) {
            e.logT()
        }
    }

    private fun checkPushStatusWhenSurfaceCreated() {
        handler.postDelayed(this, 1000)
        pusher.setAutoFocus(true)
    }

    override fun run() {
        handler.removeCallbacksAndMessages(null)
        "pusher.isNetworkPushing = ${pusher.isNetworkPushing}".logE()
        if (pusher.isNetworkPushing) {
            btnStartPush.visibility = View.GONE
            btnStopPush.visibility = View.VISIBLE
        } else {
            btnStartPush.visibility = View.VISIBLE
            btnStopPush.visibility = View.GONE
        }
        checkPushStatusWhenSurfaceCreated()
    }

    override fun onNetworkPoor(pusher: AlivcLivePusher) {
        "网络差，请退出或者重连".logE()
    }

    override fun onNetworkRecovery(pusher: AlivcLivePusher) {
        "网络恢复".logE()
    }

    override fun onReconnectStart(pusher: AlivcLivePusher) {

        "重连开始".logE()
    }

    override fun onReconnectFail(pusher: AlivcLivePusher) {
        "重连失败".logE()
        showFailDialog()
    }


    override fun onReconnectSucceed(pusher: AlivcLivePusher) {
        "重连成功".logE()
    }

    override fun onSendDataTimeout(pusher: AlivcLivePusher) {
        "发送数据超时".logE()
    }

    override fun onConnectFail(pusher: AlivcLivePusher) {
        "连接失败".logE()
    }

    override fun onConnectionLost(pusher: AlivcLivePusher) {
        "推流已断开".logE()
    }

    override fun onPushURLAuthenticationOverdue(pusher: AlivcLivePusher): String {
        "流即将过期，请更换url".logE()
        return ""
    }

    override fun onSendMessage(pusher: AlivcLivePusher) {
        "发送消息".logE()
    }

    override fun onPacketsLost(pusher: AlivcLivePusher) {
        "推流丢包通知".logE()
    }

    private fun showFailDialog() {
        runOnUiThread {
            QMUIDialog.MessageDialogBuilder(this)
                .setTitle("直播重试")
                .setMessage("网络异常，直播重连失败......")
                .addAction("Ok") { dialog, _ ->
                    try {
                        pusher.restartPushAync()
                    } catch (e: Exception) {
                        e.logE()
                    }
                    dialog.dismiss()
                }.create().apply {
                    setCanceledOnTouchOutside(false)
                    setCancelable(false)
                    showWithImmersiveCheck()
                }
        }
    }
}