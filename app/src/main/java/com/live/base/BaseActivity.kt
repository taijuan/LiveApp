package com.live.base

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.live.R
import com.live.app.app
import com.live.utils.onClick
import com.live.utils.pop
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton
import com.qmuiteam.qmui.layout.QMUIButton
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.qmuiteam.qmui.widget.QMUITopBarLayout


abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        QMUIStatusBarHelper.setStatusBarLightMode(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }
}

fun QMUITopBarLayout.title(
    res: Int,
    textColor: Int = R.color.textBlack,
    gravity: Int = Gravity.CENTER
) {
    this.setTitle(res).apply {
        typeface = ResourcesCompat.getFont(this.context, R.font.lato_bold)
        this.setTextColor(ContextCompat.getColor(app, textColor))
    }
    this.setTitleGravity(gravity)
}

fun QMUITopBarLayout.back(res: Int = R.drawable.hk_back) {
    this.addLeftImageButton(res, R.integer.id_back).apply {
        onClick({
            this.pop()
        })
    }
}

fun QMUITopBarLayout.add(): QMUIAlphaImageButton {
    return this.addRightImageButton(R.drawable.hk_add, R.integer.id_add)
}

fun QMUITopBarLayout.next(): Button {
    val button = QMUIButton(context)
    button.setChangeAlphaWhenPress(true)
    button.minWidth = 0
    button.minHeight = 0
    button.minimumWidth = 0
    button.minimumHeight = 0
    val padding = QMUIDisplayHelper.dp2px(context, 12)
    button.setPadding(padding, 0, padding, 0)
    button.setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundBlue))
    button.radius = padding
    button.setTextColor(ContextCompat.getColor(context, R.color.textWhite))
    button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    button.gravity = Gravity.CENTER
    button.setText(R.string.next)
    button.typeface = ResourcesCompat.getFont(context, R.font.lato_regular)
    val lp =
        RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            QMUIDisplayHelper.dp2px(context, 24)
        )
    lp.topMargin = QMUIDisplayHelper.dp2px(context, 12)
    lp.rightMargin = QMUIDisplayHelper.dp2px(context, 12)
    this.addRightView(button, R.integer.id_next, lp)
    return button
}


fun QMUITopBarLayout.save(): Button {
    val button = QMUIButton(context)
    button.setChangeAlphaWhenPress(true)
    button.minWidth = 0
    button.minHeight = 0
    button.minimumWidth = 0
    button.minimumHeight = 0
    val padding = QMUIDisplayHelper.dp2px(context, 12)
    button.setPadding(padding, 0, padding, 0)
    button.setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundBlue))
    button.radius = padding
    button.setTextColor(ContextCompat.getColor(context, R.color.textWhite))
    button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    button.gravity = Gravity.CENTER
    button.setText(R.string.save)
    button.typeface = ResourcesCompat.getFont(context, R.font.lato_regular)
    val lp =
        RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            QMUIDisplayHelper.dp2px(context, 24)
        )
    lp.topMargin = QMUIDisplayHelper.dp2px(context, 12)
    lp.rightMargin = QMUIDisplayHelper.dp2px(context, 12)
    this.addRightView(button, R.integer.id_save, lp)
    return button
}

fun QMUITopBarLayout.done(): Button {
    val button = QMUIButton(context)
    button.setChangeAlphaWhenPress(true)
    button.minWidth = 0
    button.minHeight = 0
    button.minimumWidth = 0
    button.minimumHeight = 0
    val padding = QMUIDisplayHelper.dp2px(context, 12)
    button.setPadding(padding, 0, padding, 0)
    button.setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundBlue))
    button.radius = padding
    button.setTextColor(ContextCompat.getColor(context, R.color.textWhite))
    button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    button.gravity = Gravity.CENTER
    button.setText(R.string.done)
    button.typeface = ResourcesCompat.getFont(context, R.font.lato_regular)
    val lp =
        RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            QMUIDisplayHelper.dp2px(context, 24)
        )
    lp.topMargin = QMUIDisplayHelper.dp2px(context, 12)
    lp.rightMargin = QMUIDisplayHelper.dp2px(context, 12)
    this.addRightView(button, R.integer.id_done, lp)
    return button
}