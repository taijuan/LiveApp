package com.live.utils

import android.net.Uri
import android.view.TextureView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.live.BuildConfig
import com.live.app.app


val exoPlayer: SimpleExoPlayer by lazy {
    ExoPlayerFactory.newSimpleInstance(
        app,
        DefaultRenderersFactory(app),
        DefaultTrackSelector(AdaptiveTrackSelection.Factory()),
        DefaultLoadControl.Builder()
            .setTargetBufferBytes(100 * 1024 * 1024)
            .createDefaultLoadControl()
    ).apply {
        repeatMode = Player.REPEAT_MODE_OFF
        playWhenReady = true
        "exoPlayer init success".logE()
    }
}

private var currentUrl: String? = null
fun videoPrepare(url: String) {
    currentUrl = url
    val dataSourceFactory = DefaultDataSourceFactory(
        app,
        Util.getUserAgent(app, BuildConfig.APPLICATION_ID)
    )
    val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(Uri.parse(url))
    exoPlayer.prepare(videoSource)
}

fun livePrepare(url: String) {
    val dataSourceFactory = RtmpDataSourceFactory()
    val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(Uri.parse(url))
    exoPlayer.prepare(videoSource)
}

fun videoRestart() {
    if (!currentUrl.isNullOrEmpty()) {
        val dataSourceFactory = DefaultDataSourceFactory(
            app,
            Util.getUserAgent(app, BuildConfig.APPLICATION_ID)
        )
        val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(currentUrl))
        exoPlayer.prepare(videoSource)
        exoPlayer.playWhenReady = true
    }
}

fun TextureView.videoWithExo() {
    exoPlayer.setVideoTextureView(this)
}

fun videoSeekTo(position: Long) {
    exoPlayer.seekTo(position)
}

fun videoOnPause() {
    if (exoPlayer.playWhenReady) {
        exoPlayer.playWhenReady = false
    }
}


fun videoOnResume() {
    if (!exoPlayer.playWhenReady) {
        exoPlayer.playWhenReady = true
    }
}

fun videoRelease() {
    exoPlayer.stop()
}

fun videoIsPlaying() = exoPlayer.playWhenReady