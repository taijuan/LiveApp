package com.live.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.R
import com.live.model.LiveData
import com.live.utils.loadImageCenterCrop
import com.live.utils.onClick
import com.live.utils.push
import com.live.VideoPlayActivity
import kotlinx.android.synthetic.main.view_holder_history.view.*


class HistoryLiveAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data = ArrayList<LiveData>()
    fun refresh(data: List<LiveData>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun loadMore(data: List<LiveData>) {
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
    private lateinit var data: LiveData
    fun setData(data: LiveData) {
        this.data = data
        itemView.imgCover.loadImageCenterCrop(data.imgUrl)
        itemView.tvTitle.text = data.title
        itemView.tvTime.text = data.time
    }

    init {
        itemView.btnPlay.onClick({
            itemView.push(VideoPlayActivity::class.java)
        })
    }

}