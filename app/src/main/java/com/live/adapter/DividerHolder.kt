package com.live.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qmuiteam.qmui.util.QMUIDisplayHelper

class DividerHolder(parent: ViewGroup) : RecyclerView.ViewHolder(View(parent.context).apply {
    layoutParams = RecyclerView.LayoutParams(-1, QMUIDisplayHelper.dp2px(context, 16))
})