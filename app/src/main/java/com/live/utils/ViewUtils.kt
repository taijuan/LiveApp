package com.live.utils

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

/**
 * 防止抖动点击，多次访问问题
 */
fun View.onClick(body: (View) -> Unit, duration: Long = 500L) {
    var m = 0L
    this.setOnClickListener {
        val c = System.currentTimeMillis()
        if (c - m >= duration) {
            body.invoke(this)
            m = c
        }
    }
}

/**
 * ImageView图片加载
 */
fun ImageView.loadImageCenterCrop(imageUrl: Any) {
    GlideApp.with(this)
        .load(imageUrl)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImageFitCenter(imageUrl: Any) {
    GlideApp.with(this)
        .load(imageUrl)
        .fitCenter()
        .into(this)
}

fun ImageView.loadImageCircleCrop(imageUrl: Any) {
    GlideApp.with(this)
        .load(imageUrl)
        .circleCrop()
        .into(this)
}