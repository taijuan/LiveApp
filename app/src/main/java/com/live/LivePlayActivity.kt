package com.live

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.video.VideoListener
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.utils.*
import com.live.widget.RESIZE_MODE_FILL
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.android.synthetic.main.activity_live_play.*


class LivePlayActivity : BaseActivity(), VideoListener, Player.EventListener {

    private var showBar = false
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_play)
        topBar.title(R.string.app_name)
        topBar.back()
        aspectRatioFrameLayout.resizeMode = RESIZE_MODE_FILL
        exoPlayer.addVideoListener(this)
        exoPlayer.addListener(this)
        playerView.videoWithExo()
        livePrepare("rtmp://pull.cdhker.com/test0806/test06?auth_key=1565082602000-0-0-abaf2cdf6c392c985509ebc92e639852")
        controller.onClick({
            showBar()
        })
    }

    private fun showBar() {
        if (showBar) {
            return
        }
        showBar = true
        topBar.visibility = View.VISIBLE
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
        videoRelease()
        exoPlayer.removeVideoListener(this)
        exoPlayer.removeListener(this)
        handler.removeCallbacksAndMessages(null)
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
                if (playWhenReady) {
                    liveNotStart()
                }
            }
            Player.STATE_BUFFERING -> {
                if (playWhenReady) {
                    liveLoading()
                } else {
                    livePause()
                }
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

    private fun liveNotStart() {
        loadingView.visibility = View.GONE
        QMUIDialog.MessageDialogBuilder(this).setMessage("直播未开始......")
            .addAction("Ok") { _, _ ->
                onBackPressed()
            }.create().apply {
                setCanceledOnTouchOutside(false)
                setCancelable(false)
                showWithImmersiveCheck()
            }
    }

    private fun liveLoading() {
        loadingView.visibility = View.VISIBLE
        loadingView.start()
    }

    private fun livePause() {
        loadingView.visibility = View.GONE
    }

    private fun livePlay() {
        loadingView.visibility = View.GONE
    }

    private fun liveEnd() {
        loadingView.visibility = View.GONE
        QMUIDialog.MessageDialogBuilder(this).setMessage("直播结束......")
            .addAction("Ok") { _, _ ->
                onBackPressed()
            }.create().apply {
                setCanceledOnTouchOutside(false)
                setCancelable(false)
                showWithImmersiveCheck()
            }
    }
}