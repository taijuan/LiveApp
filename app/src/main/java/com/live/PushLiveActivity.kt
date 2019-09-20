package com.live

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Surface
import android.view.SurfaceHolder
import com.alivc.component.custom.AlivcLivePushCustomDetect
import com.alivc.component.custom.AlivcLivePushCustomFilter
import com.alivc.live.detect.TaoFaceFilter
import com.alivc.live.filter.TaoBeautyFilter
import com.alivc.live.pusher.*
import com.google.android.exoplayer2.C
import com.live.app.app
import com.live.base.BaseActivity
import com.live.fragment.PushLiveLandFragment
import com.live.fragment.PushLivePortFragment
import com.live.utils.HandlerUtils
import com.live.utils.logE
import com.live.utils.logT
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_push_live.*

const val ORIENTATION_KEY = "orientation_key"


class PushLiveActivity : BaseActivity(), SurfaceHolder.Callback,
    AlivcLivePushNetworkListener {

    private val pusher: AlivcLivePusher = AlivcLivePusher()
    private var isFlash = false
    private val pushConfig: AlivcLivePushConfig by lazy {
        AlivcLivePushConfig().apply {
            setPreviewOrientation(AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT)
            previewDisplayMode = AlivcPreviewDisplayMode.ALIVC_LIVE_PUSHER_PREVIEW_ASPECT_FILL
            setCameraType(AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT)
            setResolution(AlivcResolutionEnum.RESOLUTION_SELFDEFINE)
            qualityMode = AlivcQualityModeEnum.QM_RESOLUTION_FIRST
            beautyLevel = AlivcBeautyLevelEnum.BEAUTY_Normal
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
        }
    }
    private var zoom = 0
    private var zoomAction = true
    private var isStartLive = false
    private var time = 0L
    private val handlerUtils: HandlerUtils by lazy {
        HandlerUtils(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_live)
        timePlus()
        pusher.init(this, pushConfig)
        pusher.setLivePushNetworkListener(this)
        pushView.holder.addCallback(this)
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

    fun switchFlash() {
        isFlash = !isFlash
        pusher.setFlash(isFlash)
    }

    fun isSupportFlash() =
        pushConfig.cameraType == AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK.cameraId

    fun switchCamera(onDone: (Boolean) -> Unit) {
        pushConfig.cameraType.logE()
        try {
            isFlash = false
            pusher.setFlash(false)
            pusher.switchCamera()
        } catch (e: Exception) {
            e.logT()
        } finally {
            onDone.invoke(pushConfig.cameraType == AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK.cameraId)
            pushConfig.cameraType.logE()
        }
    }

    fun isStartLive() = isStartLive
    fun switchLive(onDone: (Boolean) -> Unit) {
        if (!isStartLive)
            isStartLive = try {
                pusher.startPushAysnc("rtmp://push.cdhker.com/test0806/test06?auth_key=1565082602000-0-0-1bf369caf6b774c961359ce39a61cca7")
                true
            } catch (e: Exception) {
                e.logT()
                try {
                    pusher.restartPushAync()
                    true
                } catch (e: Exception) {
                    e.logT()
                    false
                }
            } else {
            try {
                pusher.stopPush()
                isStartLive = false
            } catch (e: Exception) {
                e.logE()
            }
        }
        onDone.invoke(isStartLive)
    }

    private fun getStringForTime(timeMs: Long): String {
        var ms = timeMs
        if (ms == C.TIME_UNSET) {
            ms = 0
        }
        val totalSeconds = (ms + 500) / 1000
        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600
        return String.format("%d:%02d:%02d", hours, minutes, seconds)

    }

    fun time(): String {
        time.logE()
        return getStringForTime(time)
    }

    private fun timePlus() {
        handlerUtils.postDelayed({
            if (isStartLive) {
                time += 1000
            }
            timePlus()
        }, 1000)
    }

    private fun replaceFragment() {
        window.decorView.postDelayed({
            when (windowManager.defaultDisplay.rotation) {
                Surface.ROTATION_90 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_controller, PushLiveLandFragment())
                        .commitNow()
                }
                Surface.ROTATION_270 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_controller, PushLiveLandFragment())
                        .commitNow()
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_controller, PushLivePortFragment())
                        .commitNow()
                }

            }
        }, 100)
    }


    fun setZoom() {
        if (zoom == 11) {
            zoomAction = false
        }
        if (zoom == 0) {
            zoomAction = true
        }
        if (zoomAction) {
            zoom++
        } else {
            zoom--
        }
        pusher.setZoom(zoom * 9)

    }

    override fun onDestroy() {
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
    override fun surfaceChanged(
        surfaceHolder: SurfaceHolder?,
        format: Int,
        width: Int,
        height: Int
    ) {
        "width = $width ; height = $height".logE()
        replaceFragment()
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder?) {
        "surfaceDestroyed".logE()
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder?) {
        "surfaceCreated".logE()
        try {
            if (pusher.currentStatus == AlivcLivePushStats.INIT) {
                pusher.startPreviewAysnc(pushView)
            }
        } catch (e: Exception) {
            e.logT()
        }
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
        try {
            pusher.restartPushAync()
        } catch (e: Exception) {
            e.logE()
            try {
                pusher.restartPushAync()
            } catch (e: Exception) {
                e.logE()
            }
        }
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

    override fun onBackPressed() {
        if (isStartLive()) {
            XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asConfirm("直播提示", "正在直播，请确认是否退出，退出将结束直播...") {
                    super.onBackPressed()
                }.show()
        } else {
            super.onBackPressed()
        }
    }
}
