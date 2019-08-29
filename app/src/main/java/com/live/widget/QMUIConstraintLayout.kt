package com.live.widget

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

import androidx.annotation.ColorInt

import com.qmuiteam.qmui.layout.IQMUILayout
import com.qmuiteam.qmui.layout.QMUILayoutHelper


class QMUIConstraintLayout : QMUIAlphaConstraintLayout, IQMUILayout {
    private lateinit var layoutHelper: QMUILayoutHelper

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        layoutHelper = QMUILayoutHelper(context, attrs, 0, this)
        setChangeAlphaWhenDisable(false)
        setChangeAlphaWhenPress(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = widthMeasureSpec
        var height = heightMeasureSpec
        width = layoutHelper.getMeasuredWidthSpec(width)
        height = layoutHelper.getMeasuredHeightSpec(height)
        super.onMeasure(width, height)
        val minW = layoutHelper.handleMiniWidth(width, measuredWidth)
        val minH = layoutHelper.handleMiniHeight(height, measuredHeight)
        if (width != minW || height != minH) {
            super.onMeasure(minW, minH)
        }
    }

    override fun updateTopDivider(
        topInsetLeft: Int,
        topInsetRight: Int,
        topDividerHeight: Int,
        topDividerColor: Int
    ) {
        layoutHelper.updateTopDivider(
            topInsetLeft,
            topInsetRight,
            topDividerHeight,
            topDividerColor
        )
        invalidate()
    }

    override fun updateBottomDivider(
        bottomInsetLeft: Int,
        bottomInsetRight: Int,
        bottomDividerHeight: Int,
        bottomDividerColor: Int
    ) {
        layoutHelper.updateBottomDivider(
            bottomInsetLeft,
            bottomInsetRight,
            bottomDividerHeight,
            bottomDividerColor
        )
        invalidate()
    }

    override fun updateLeftDivider(
        leftInsetTop: Int,
        leftInsetBottom: Int,
        leftDividerWidth: Int,
        leftDividerColor: Int
    ) {
        layoutHelper.updateLeftDivider(
            leftInsetTop,
            leftInsetBottom,
            leftDividerWidth,
            leftDividerColor
        )
        invalidate()
    }

    override fun updateRightDivider(
        rightInsetTop: Int,
        rightInsetBottom: Int,
        rightDividerWidth: Int,
        rightDividerColor: Int
    ) {
        layoutHelper.updateRightDivider(
            rightInsetTop,
            rightInsetBottom,
            rightDividerWidth,
            rightDividerColor
        )
        invalidate()
    }

    override fun onlyShowTopDivider(
        topInsetLeft: Int, topInsetRight: Int,
        topDividerHeight: Int, topDividerColor: Int
    ) {
        layoutHelper.onlyShowTopDivider(
            topInsetLeft,
            topInsetRight,
            topDividerHeight,
            topDividerColor
        )
        invalidate()
    }

    override fun onlyShowBottomDivider(
        bottomInsetLeft: Int, bottomInsetRight: Int,
        bottomDividerHeight: Int, bottomDividerColor: Int
    ) {
        layoutHelper.onlyShowBottomDivider(
            bottomInsetLeft,
            bottomInsetRight,
            bottomDividerHeight,
            bottomDividerColor
        )
        invalidate()
    }

    override fun onlyShowLeftDivider(
        leftInsetTop: Int,
        leftInsetBottom: Int,
        leftDividerWidth: Int,
        leftDividerColor: Int
    ) {
        layoutHelper.onlyShowLeftDivider(
            leftInsetTop,
            leftInsetBottom,
            leftDividerWidth,
            leftDividerColor
        )
        invalidate()
    }

    override fun onlyShowRightDivider(
        rightInsetTop: Int,
        rightInsetBottom: Int,
        rightDividerWidth: Int,
        rightDividerColor: Int
    ) {
        layoutHelper.onlyShowRightDivider(
            rightInsetTop,
            rightInsetBottom,
            rightDividerWidth,
            rightDividerColor
        )
        invalidate()
    }


    override fun setTopDividerAlpha(dividerAlpha: Int) {
        layoutHelper.setTopDividerAlpha(dividerAlpha)
        invalidate()
    }

    override fun setBottomDividerAlpha(dividerAlpha: Int) {
        layoutHelper.setBottomDividerAlpha(dividerAlpha)
        invalidate()
    }

    override fun setLeftDividerAlpha(dividerAlpha: Int) {
        layoutHelper.setLeftDividerAlpha(dividerAlpha)
        invalidate()
    }

    override fun setRightDividerAlpha(dividerAlpha: Int) {
        layoutHelper.setRightDividerAlpha(dividerAlpha)
        invalidate()
    }

    override fun setRadiusAndShadow(radius: Int, shadowElevation: Int, shadowAlpha: Float) {
        layoutHelper.setRadiusAndShadow(radius, shadowElevation, shadowAlpha)
    }

    override fun setRadiusAndShadow(
        radius: Int, @IQMUILayout.HideRadiusSide hideRadiusSide: Int,
        shadowElevation: Int,
        shadowAlpha: Float
    ) {
        layoutHelper.setRadiusAndShadow(radius, hideRadiusSide, shadowElevation, shadowAlpha)
    }

    override fun setRadiusAndShadow(
        radius: Int,
        hideRadiusSide: Int,
        shadowElevation: Int,
        shadowColor: Int,
        shadowAlpha: Float
    ) {
        layoutHelper.setRadiusAndShadow(
            radius,
            hideRadiusSide,
            shadowElevation,
            shadowColor,
            shadowAlpha
        )
    }

    override fun setRadius(radius: Int) {
        layoutHelper.radius = radius
    }

    override fun setRadius(radius: Int, @IQMUILayout.HideRadiusSide hideRadiusSide: Int) {
        layoutHelper.setRadius(radius, hideRadiusSide)
    }

    override fun getRadius(): Int {
        return layoutHelper.radius
    }

    override fun setOutlineInset(left: Int, top: Int, right: Int, bottom: Int) {
        layoutHelper.setOutlineInset(left, top, right, bottom)
    }

    override fun setHideRadiusSide(hideRadiusSide: Int) {
        layoutHelper.hideRadiusSide = hideRadiusSide
    }

    override fun getHideRadiusSide(): Int {
        return layoutHelper.hideRadiusSide
    }

    override fun setBorderColor(@ColorInt borderColor: Int) {
        layoutHelper.setBorderColor(borderColor)
        invalidate()
    }

    override fun setBorderWidth(borderWidth: Int) {
        layoutHelper.setBorderWidth(borderWidth)
        invalidate()
    }

    override fun setShowBorderOnlyBeforeL(showBorderOnlyBeforeL: Boolean) {
        layoutHelper.setShowBorderOnlyBeforeL(showBorderOnlyBeforeL)
        invalidate()
    }

    override fun setWidthLimit(widthLimit: Int): Boolean {
        if (layoutHelper.setWidthLimit(widthLimit)) {
            requestLayout()
            invalidate()
        }
        return true
    }

    override fun setHeightLimit(heightLimit: Int): Boolean {
        if (layoutHelper.setHeightLimit(heightLimit)) {
            requestLayout()
            invalidate()
        }
        return true
    }

    override fun setUseThemeGeneralShadowElevation() {
        layoutHelper.setUseThemeGeneralShadowElevation()
    }

    override fun setOutlineExcludePadding(outlineExcludePadding: Boolean) {
        layoutHelper.setOutlineExcludePadding(outlineExcludePadding)
    }

    override fun setShadowElevation(elevation: Int) {
        layoutHelper.shadowElevation = elevation
    }

    override fun getShadowElevation(): Int {
        return layoutHelper.shadowElevation
    }

    override fun setShadowAlpha(shadowAlpha: Float) {
        layoutHelper.shadowAlpha = shadowAlpha
    }

    override fun getShadowAlpha(): Float {
        return layoutHelper.shadowAlpha
    }

    override fun setShadowColor(shadowColor: Int) {
        layoutHelper.shadowColor = shadowColor
    }

    override fun getShadowColor(): Int {
        return layoutHelper.shadowColor
    }

    override fun setOuterNormalColor(color: Int) {
        layoutHelper.setOuterNormalColor(color)
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        layoutHelper.drawDividers(canvas, width, height)
        layoutHelper.dispatchRoundBorderDraw(canvas)
    }
}
