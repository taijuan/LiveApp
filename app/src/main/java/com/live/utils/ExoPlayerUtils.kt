package com.live.utils

import android.net.Uri
import android.view.TextureView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import com.live.app.app
import java.io.File


private val cache = SimpleCache(
    File(app.cacheDir, "videoCache"),
    LeastRecentlyUsedCacheEvictor((1024 * 1024 * 1024).toLong()),
    ExoDatabaseProvider(app)
)
val exoPlayer: SimpleExoPlayer by lazy {
    ExoPlayerFactory.newSimpleInstance(
        app,
        DefaultRenderersFactory(app),
        DefaultTrackSelector(AdaptiveTrackSelection.Factory()),
        DefaultLoadControl.Builder().createDefaultLoadControl()
    ).apply {
        repeatMode = Player.REPEAT_MODE_OFF
        playWhenReady = true
        "exoPlayer init success".logE()
    }
}

fun videoPrepare(url: String) {
    val videoSource = ProgressiveMediaSource.Factory(
        CacheDataSourceFactory(
            cache, DefaultDataSourceFactory(
                app,
                Util.getUserAgent(app, "LiveApp")
            )
        )
    ).createMediaSource(Uri.parse(url))
    exoPlayer.prepare(videoSource)
}

fun videoRestart() {
    exoPlayer.playWhenReady = true
    exoPlayer.seekTo(0)
}

fun livePrepare(url: String) {
    val dataSourceFactory = RtmpDataSourceFactory()
    val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(Uri.parse(url))
    exoPlayer.prepare(videoSource)
}


fun TextureView.videoSetVideoTextureView() {
    exoPlayer.setVideoTextureView(this)
}

fun TextureView.videoClearVideoTextureView() {
    exoPlayer.clearVideoTextureView(this)
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