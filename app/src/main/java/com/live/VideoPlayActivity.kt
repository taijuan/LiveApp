package com.live

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.video.VideoListener
import com.live.base.BaseActivity
import com.live.utils.*
import com.live.widget.RESIZE_MODE_FIT
import com.live.widget.createShareDialog
import kotlinx.android.synthetic.main.activity_video_play.*

const val PLAYBACK_DATA_URL = "playback_data-url"
class VideoPlayActivity : BaseActivity(), VideoListener, Player.EventListener,
    SeekBar.OnSeekBarChangeListener {
    private val loadingDrawable: AnimationDrawable by lazy {
        this.getDrawable(R.drawable.hk_loading) as AnimationDrawable
    }
    private var playStatus = 0
    private var showBar = false
    private val handler: HandlerUtils by lazy {
        HandlerUtils(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        topBar.setBackgroundAlpha(0)
        tvTitle.apply {
            text = "Video Play"
            onClick({
                pop()
            })
        }
        btnShare.onClick({
            createShareDialog(requestedOrientation)
        })
        aspectRatioFrameLayout.resizeMode = RESIZE_MODE_FIT
        exoPlayer.addVideoListener(this)
        exoPlayer.addListener(this)
        playerView.videoSetVideoTextureView()
        videoPrepare(intent.getStringExtra(PULL_DATA_URL)?:"")
        controller.onClick({
            showBar()
        })
        setBtnFullText()
        btnScreen.apply {
            isSelected = true
            onClick({
                switchFullScreen()
            })
        }
        btnAction.onClick({
            when (playStatus) {
                1 -> videoOnResume()
                2 -> videoOnPause()
                3 -> videoRestart()
            }
        })
        btnPlayAndPause.onClick({
            when (playStatus) {
                1 -> videoOnResume()
                2 -> videoOnPause()
                3 -> videoRestart()
            }
        })
        loadingStart()
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
        bottomBar.visibility = View.GONE
        btnAction.visibility = View.GONE
    }

    private fun switchFullScreen() {
        requestedOrientation =
            if (requestedOrientation == SCREEN_ORIENTATION_PORTRAIT) SCREEN_ORIENTATION_LANDSCAPE else SCREEN_ORIENTATION_PORTRAIT
        setBtnFullText()
    }

    private fun setBtnFullText() {
        btnScreen.isSelected = requestedOrientation == SCREEN_ORIENTATION_PORTRAIT
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
        loadingStop()
        exoPlayer.removeVideoListener(this)
        exoPlayer.removeListener(this)
        playerView.videoClearVideoTextureView()
        videoRelease()
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
                videoPause()
            }
            Player.STATE_BUFFERING -> {
                videoLoading()
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
                tvTime.visibility = View.VISIBLE
                timeBar.visibility = View.VISIBLE
                tvTime.text = "%s/%s".format(getStringForTime(progress), getStringForTime(total))
                timeBar.max = total.toInt()
                timeBar.progress = progress.toInt()
                timeBar.secondaryProgress = secondaryProgress.toInt()
            } else {
                tvTime.visibility = View.GONE
                timeBar.visibility = View.GONE
            }
            setTimeBar()
        }, 300)
    }

    private fun videoLoading() {
        playStatus = 0
        loadingStart()
        btnPlayAndPause.setImageResource(R.drawable.hk_bottom_play)
    }

    private fun loadingStart() {
        btnAction.setImageDrawable(loadingDrawable)
        if (!loadingDrawable.isRunning) {
            loadingDrawable.start()
        }
    }

    private fun videoPause() {
        playStatus = 1
        loadingStop()
        btnAction.setImageResource(R.drawable.hk_play)
        btnPlayAndPause.setImageResource(R.drawable.hk_bottom_play)
    }

    private fun videoPlay() {
        playStatus = 2
        loadingStop()
        btnAction.setImageResource(R.drawable.hk_pause)
        btnPlayAndPause.setImageResource(R.drawable.hk_bottom_pause)
    }

    private fun videoEnd() {
        playStatus = 3
        loadingStop()
        btnAction.setImageResource(R.drawable.hk_replay)
        btnPlayAndPause.setImageResource(R.drawable.hk_bottom_play)
    }

    private fun loadingStop() {
        btnAction.setImageDrawable(null)
        if (loadingDrawable.isRunning) {
            loadingDrawable.stop()
        }
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