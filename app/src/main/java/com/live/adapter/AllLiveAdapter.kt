package com.live.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.*
import com.live.model.LiveDataRes
import com.live.model.LiveStatus
import com.live.utils.loadImageCenterCrop
import com.live.utils.onClick
import com.live.utils.push
import com.live.utils.showToast
import kotlinx.android.synthetic.main.view_holder_all_live.view.*

class AllLiveAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        else -> AllLiveHolder(parent)
    }

    override fun getItemCount() = data.size * 2 + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AllLiveHolder) {
            holder.setData(this.data[position / 2])
        }
    }

    override fun getItemViewType(position: Int) = if (position % 2 == 1) {
        VIEW_TYPE_ALL_LIVE
    } else {
        VIEW_TYPE_DIVIDER
    }
}

class AllLiveHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.view_holder_all_live,
            parent,
            false
        )
    ) {
    private lateinit var data: LiveDataRes
    fun setData(data: LiveDataRes) {
        this.data = data
        itemView.imgCover.loadImageCenterCrop(data.coverImg)
        itemView.tvTitle.text = data.title
        itemView.tvTime.text = data.createTime
        when (data.status()) {
            LiveStatus.PENDING -> {
                itemView.tvStatus.setText(R.string.pending)
                itemView.tvStatus.setBackgroundResource(R.color.backgroundRed)
            }
            LiveStatus.LIVING -> {
                itemView.tvStatus.setText(R.string.live)
                itemView.tvStatus.setBackgroundResource(R.color.backgroundBlue)
            }
            LiveStatus.PLAYBACK -> {
                itemView.tvStatus.setText(R.string.playback)
                itemView.tvStatus.setBackgroundResource(R.color.backgroundBlue)
            }
        }
    }

    init {
        itemView.btnStart.onClick({
            when (data.status()) {
                LiveStatus.PENDING -> {
                    "直播未开始".showToast()
                }
                LiveStatus.LIVING -> {
                    itemView.push(LivePlayActivity::class.java, Bundle().apply {
                        putString(PULL_DATA_URL, data.getPullUrl())
                    })
                }
                LiveStatus.PLAYBACK -> {
                    itemView.push(VideoPlayActivity::class.java,Bundle().apply {
                        putString(PLAYBACK_DATA_URL, data.getPullUrl())
                    })
                }
            }
        })
    }

}