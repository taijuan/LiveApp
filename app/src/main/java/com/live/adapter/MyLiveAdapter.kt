package com.live.adapter

import android.Manifest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.LiveOptionsActivity
import com.live.PushLiveActivity
import com.live.R
import com.live.model.LiveData
import com.live.permission.request
import com.live.utils.loadImageCenterCrop
import com.live.utils.onClick
import com.live.utils.push
import kotlinx.android.synthetic.main.view_holder_my_live.view.*

class MyLiveAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        0 -> DividerHolder(parent)
        else -> MyLiveHolder(parent)
    }

    override fun getItemCount() = data.size * 2 + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyLiveHolder) {
            holder.setData(this.data[position / 2])
        }
    }

    override fun getItemViewType(position: Int) = if (position % 2 == 1) {
        VIEW_TYPE_MY_LIVE
    } else {
        VIEW_TYPE_DIVIDER
    }

}

class MyLiveHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.view_holder_my_live,
            parent,
            false
        )
    ) {
    private lateinit var data: LiveData
    fun setData(data: LiveData) {
        this.data = data
        itemView.imgCover.loadImageCenterCrop(data.imgUrl)
        itemView.tvTitle.text = data.title
        itemView.tvEvent.text = itemView.resources.getString(R.string.event_xxx, data.event)
        itemView.tvSession.text = itemView.resources.getString(R.string.session_xxx, data.session)
        itemView.tvDate.text = data.date
        itemView.tvTime.text = data.time
    }

    init {
        itemView.btnQrCode.onClick({

        })
        itemView.btnOptions.onClick({
            itemView.push(LiveOptionsActivity::class.java)
        })
        itemView.btnGo.setChangeAlphaWhenPress(true)
        itemView.btnGo.onClick({
            itemView.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                onGranted = {
                    itemView.push(PushLiveActivity::class.java)
                })
        })
    }

}