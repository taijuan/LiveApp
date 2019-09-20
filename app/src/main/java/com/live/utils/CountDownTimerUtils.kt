package com.live.utils

import android.os.CountDownTimer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class CountDownTimerUtils(
    owner: LifecycleOwner,
    totalTime: Long = 60 * 1000,
    interval: Long = 1000
) : CountDownTimer(totalTime, interval), LifecycleObserver {
    private var onTick: ((Long) -> Unit)? = null
    private var onFinish: (() -> Unit)? = null

    init {
        owner.lifecycle.addObserver(this)
    }

    fun setOnTick(onTick: (Long) -> Unit) {
        this.onTick = onTick
    }

    fun setOnFinish(onFinish: () -> Unit) {
        this.onFinish = onFinish
    }

    override fun onFinish() {
        onFinish?.invoke()
    }

    override fun onTick(time: Long) {
        onTick?.invoke(time)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        cancel()
    }
}