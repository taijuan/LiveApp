package com.live.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.IntDef
import com.live.R

/**
 * A [FrameLayout] that resizes itself to match a specified aspect ratio.
 */
class AspectRatioFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var videoAspectRatio: Float = 0.toFloat()
    @ResizeMode
    var resizeMode: Int = RESIZE_MODE_FIT
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }


    init {
        resizeMode = RESIZE_MODE_FIT
        if (attrs != null) {
            val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.AspectRatioFrameLayout, 0, 0
            )
            try {
                resizeMode =
                    a.getInt(R.styleable.AspectRatioFrameLayout_resize_mode, RESIZE_MODE_FIT)
            } finally {
                a.recycle()
            }
        }
    }

    /**
     * Sets the aspect ratio that this view should satisfy.
     *
     * @param widthHeightRatio The width to height ratio.
     */
    fun setAspectRatio(widthHeightRatio: Float) {
        if (this.videoAspectRatio != widthHeightRatio) {
            this.videoAspectRatio = widthHeightRatio
            requestLayout()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (videoAspectRatio <= 0) {
            // Aspect ratio not set.
            return
        }

        var width = measuredWidth
        var height = measuredHeight
        val viewAspectRatio = width.toFloat() / height
        val aspectDeformation = videoAspectRatio / viewAspectRatio - 1

        when (resizeMode) {
            RESIZE_MODE_FIXED_WIDTH -> height = (width / videoAspectRatio).toInt()
            RESIZE_MODE_FIXED_HEIGHT -> width = (height * videoAspectRatio).toInt()
            RESIZE_MODE_ZOOM -> if (aspectDeformation > 0) {
                width = (height * videoAspectRatio).toInt()
            } else {
                height = (width / videoAspectRatio).toInt()
            }
            RESIZE_MODE_FIT -> if (aspectDeformation > 0) {
                height = (width / videoAspectRatio).toInt()
            } else {
                width = (height * videoAspectRatio).toInt()
            }
            RESIZE_MODE_FILL -> {
            }
            else -> {
            }
        }
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }

}


const val RESIZE_MODE_FIT = 0
const val RESIZE_MODE_FIXED_WIDTH = 1
const val RESIZE_MODE_FIXED_HEIGHT = 2
const val RESIZE_MODE_FILL = 3
const val RESIZE_MODE_ZOOM = 4

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(
    RESIZE_MODE_FIT,
    RESIZE_MODE_FIXED_WIDTH,
    RESIZE_MODE_FIXED_HEIGHT,
    RESIZE_MODE_FILL,
    RESIZE_MODE_ZOOM
)
annotation class ResizeMode
