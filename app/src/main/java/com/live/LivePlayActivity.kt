package com.live

import android.content.pm.ActivityInfo
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.video.VideoListener
import com.live.app.app
import com.live.base.BaseActivity
import com.live.utils.*
import com.live.widget.RESIZE_MODE_FIT
import com.live.widget.createShareDialog
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import kotlinx.android.synthetic.main.activity_live_play.*


class LivePlayActivity : BaseActivity(), VideoListener, Player.EventListener {
    private val dialog: BasePopupView by lazy {
        XPopup.Builder(this)
            .dismissOnBackPressed(true)
            .dismissOnTouchOutside(true)
            .asConfirm("直播提示", "直播未开始...") {
                onBackPressed()
            }
    }
    private val loadingDrawable: AnimationDrawable by lazy {
        this.getDrawable(R.drawable.hk_loading) as AnimationDrawable
    }
    private var playStatus = 0
    private var showBar = false
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_play)
        topBar.setBackgroundAlpha(0)
        tvTitle.apply {
            text = "Live Play"
            onClick({
                pop()
            })
        }
        btnShare.onClick({
            createShareDialog(requestedOrientation)
        })
        btnAction.onClick({
            when (playStatus) {
                1 -> videoOnResume()
                2 -> videoOnPause()
                3 -> videoRestart()
            }
        })
        aspectRatioFrameLayout.resizeMode = RESIZE_MODE_FIT
        exoPlayer.addVideoListener(this)
        exoPlayer.addListener(this)
        playerView.videoSetVideoTextureView()
        livePrepare("rtmp://pull.xingguitiyu.com/xgtylive/test091925?auth_key=1568888950-0-0-4d8af9be591dd5f1a3db393e5bdd764f")
        controller.onClick({
            showBar()
        })
        loadingStart()
    }

    private fun showBar() {
        if (showBar) {
            return
        }
        showBar = true
        topBar.visibility = View.VISIBLE
        btnAction.visibility = View.VISIBLE
        handler.postDelayed({
            hideBar()
        }, 5000L)
    }

    private fun hideBar() {
        if (!showBar) {
            return
        }
        showBar = false
        topBar.visibility = View.GONE
        btnAction.visibility = View.GONE
    }

    override fun onResume() {
        videoOnResume()
        super.onResume()
    }

    override fun onPause() {
        videoOnPause()
        super.onPause()
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        exoPlayer.removeVideoListener(this)
        exoPlayer.removeListener(this)
        playerView.videoClearVideoTextureView()
        videoRelease()
        super.onDestroy()

    }


    /**
     * VideoListener
     */
    override fun onVideoSizeChanged(
        width: Int,
        height: Int,
        unappliedRotationDegrees: Int,
        pixelWidthHeightRatio: Float
    ) {
        val videoAspectRatio: Float =
            if (height == 0 || width == 0) 1f else width * pixelWidthHeightRatio / height
        aspectRatioFrameLayout.setAspectRatio(videoAspectRatio)
        requestedOrientation = if (width > height) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onSurfaceSizeChanged(width: Int, height: Int) {

    }

    override fun onRenderedFirstFrame() {
        "totalTime = ${exoPlayer.duration}"
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        "playWhenReady = $playWhenReady playbackState = $playbackState".logE()
        when (playbackState) {
            Player.STATE_IDLE -> {
                liveNotStart()
            }
            Player.STATE_BUFFERING -> {
                liveLoading()
            }
            Player.STATE_READY -> {
                if (playWhenReady) {
                    livePlay()
                } else {
                    livePause()
                }
            }
            Player.STATE_ENDED -> {
                liveEnd()
            }
        }
    }

    private fun loadingStart() {
        btnAction.setImageDrawable(loadingDrawable)
        if (!loadingDrawable.isRunning) {
            loadingDrawable.start()
        }
    }

    private fun loadingStop() {
        btnAction.setImageDrawable(null)
        if (loadingDrawable.isRunning) {
            loadingDrawable.stop()
        }
    }

    private fun liveNotStart() {
        livePause()
        if (!dialog.isShow) {
            dialog.show()
        }
    }

    private fun liveLoading() {
        playStatus = 0
        loadingStart()
    }

    private fun livePause() {
        playStatus = 1
        loadingStop()
        btnAction.setImageResource(R.drawable.hk_play)
    }

    private fun livePlay() {
        playStatus = 2
        loadingStop()
        btnAction.setImageResource(R.drawable.hk_pause)
    }


    private fun liveEnd() {
        playStatus = 3
        loadingStop()
        btnAction.setImageResource(R.drawable.hk_replay)
        XPopup.Builder(this)
            .dismissOnBackPressed(true)
            .dismissOnTouchOutside(true)
            .maxWidth(QMUIDisplayHelper.dp2px(app, 320))
            .asConfirm("直播提示", "直播已结束...") {
                onBackPressed()
            }.show()
    }
}