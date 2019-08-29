package com.live

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.video.VideoListener
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.utils.*
import com.live.widget.RESIZE_MODE_FIT
import kotlinx.android.synthetic.main.activity_video_play.*


class VideoPlayActivity : BaseActivity(), VideoListener, Player.EventListener,
    SeekBar.OnSeekBarChangeListener {

    private var showBar = false
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        topBar.title(R.string.app_name)
        topBar.back()
        aspectRatioFrameLayout.resizeMode = RESIZE_MODE_FIT
        exoPlayer.addVideoListener(this)
        exoPlayer.addListener(this)
        playerView.videoWithExo()
        videoPrepare("http://1252065688.vod2.myqcloud.com/d7dc3e4avodgzp1252065688/442c05395285890792586288323/hjRqKBsrAT4A.mov")
        controller.onClick({
            showBar()
        })
        setBtnFullText()
        btnFullScreen.onClick({
            switchFullScreen()
        })
        btnPlay.onClick({
            videoOnPause()
        })
        btnStop.onClick({
            videoOnResume()
        })
        btnEnd.onClick({
            videoRestart()
            btnEnd.visibility = View.GONE
        })
        timeBar.setOnSeekBarChangeListener(this)
        setTimeBar()
    }

    private fun showBar() {
        if (showBar) {
            return
        }
        showBar = true
        topBar.visibility = View.VISIBLE
        bottomBar.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        if (videoIsPlaying()) {
            btnPlay.visibility = View.VISIBLE
        }
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
        bottomBar.visibility = View.GONE
        btnPlay.visibility = View.GONE
    }

    private fun switchFullScreen() {
        requestedOrientation =
            if (requestedOrientation == SCREEN_ORIENTATION_PORTRAIT) SCREEN_ORIENTATION_LANDSCAPE else SCREEN_ORIENTATION_PORTRAIT
        setBtnFullText()
    }

    private fun setBtnFullText() {
        btnFullScreen.text =
            if (requestedOrientation == SCREEN_ORIENTATION_PORTRAIT) "L" else "P"
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

    override fun onBackPressed() {
        if (requestedOrientation == SCREEN_ORIENTATION_LANDSCAPE) {
            requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
            setBtnFullText()
            return
        }
        super.onBackPressed()
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
            }
            Player.STATE_BUFFERING -> {
                if (playWhenReady) {
                    videoLoading()
                } else {
                    videoPause()
                }
            }
            Player.STATE_READY -> {
                if (playWhenReady) {
                    videoPlay()
                } else {
                    videoPause()
                }
            }
            Player.STATE_ENDED -> {
                videoEnd()
            }
        }
    }

    private fun setTimeBar() {
        handler.postDelayed({
            val total = exoPlayer.duration
            val progress = exoPlayer.currentPosition
            val secondaryProgress = exoPlayer.contentBufferedPosition
            if (total > 0) {
                tvTotalTime.visibility = View.VISIBLE
                tvCurrentTime.visibility = View.VISIBLE
                timeBar.visibility = View.VISIBLE
                tvTotalTime.text = getStringForTime(total)
                tvCurrentTime.text = getStringForTime(progress)
                timeBar.max = total.toInt()
                timeBar.progress = progress.toInt()
                timeBar.secondaryProgress = secondaryProgress.toInt()
            } else {
                tvTotalTime.visibility = View.GONE
                tvCurrentTime.visibility = View.GONE
                timeBar.visibility = View.GONE
            }
            setTimeBar()
        }, 300)
    }

    private fun videoLoading() {
        loadingView.visibility = View.VISIBLE
        btnPlay.visibility = View.GONE
        btnStop.visibility = View.GONE
        loadingView.start()
    }

    private fun videoPause() {
        loadingView.visibility = View.GONE
        btnPlay.visibility = View.GONE
        btnStop.visibility = View.VISIBLE
        btnEnd.visibility = View.GONE
    }

    private fun videoPlay() {
        loadingView.visibility = View.GONE
        btnPlay.visibility = View.GONE
        btnStop.visibility = View.GONE
        btnEnd.visibility = View.GONE
    }

    private fun videoEnd() {
        loadingView.visibility = View.GONE
        btnPlay.visibility = View.GONE
        btnStop.visibility = View.GONE
        btnEnd.visibility = View.VISIBLE
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
        return if (hours > 0)
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        else
            String.format("%02d:%02d", minutes, seconds)
    }

    override fun onProgressChanged(timeBar: SeekBar?, position: Int, fromUser: Boolean) {
        if (fromUser) {
            videoSeekTo(position.toLong())
        }
    }

    override fun onStartTrackingTouch(timeBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(timeBar: SeekBar?) {

    }
}