package com.live.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.R
import com.live.model.LiveDataRes
import com.live.utils.loadImageCenterCrop
import com.live.utils.onClick
import com.live.utils.push
import com.live.VideoPlayActivity
import kotlinx.android.synthetic.main.view_holder_history.view.*


class HistoryLiveAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = ArrayList<LiveDataRes>()
    fun refresh(data: List<LiveDataRes>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun loadMore(data: List<LiveDataRes>) {
        val positionStart = this.data.size
        val itemCount = data.size
        this.data.addAll(data)
        notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_DIVIDER -> DividerHolder(parent)
        else -> HistoryLiveHolder(parent)
    }

    override fun getItemCount() = data.size * 2 + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HistoryLiveHolder) {
            holder.setData(this.data[position / 2])
        }
    }

    override fun getItemViewType(position: Int) = if (position % 2 == 1) {
        VIEW_TYPE_HISTORY
    } else {
        VIEW_TYPE_DIVIDER
    }
}

class HistoryLiveHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.view_holder_history,
            parent,
            false
        )
    ) {
    private lateinit var dataRes: LiveDataRes
    fun setData(dataRes: LiveDataRes) {
        this.dataRes = dataRes
        itemView.imgCover.loadImageCenterCrop(dataRes.coverImg)
        itemView.tvTitle.text = dataRes.title
        itemView.tvTime.text = dataRes.createTime
    }

    init {
        itemView.btnAction.onClick({
            itemView.push(VideoPlayActivity::class.java)
        })
    }

}