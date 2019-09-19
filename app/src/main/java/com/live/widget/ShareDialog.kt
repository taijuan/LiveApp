package com.live.widget

import android.content.Context
import android.content.pm.ActivityInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.live.R
import com.live.utils.onClick
import com.live.utils.showToast
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BottomPopupView
import com.lxj.xpopup.core.DrawerPopupView
import com.lxj.xpopup.enums.PopupPosition
import kotlinx.android.synthetic.main.dialog_share_drawer_view.view.*
import kotlinx.android.synthetic.main.item_share.view.*

fun FragmentActivity.createShareDialog(requestedOrientation: Int) {
    if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
        XPopup.Builder(this)
            .autoDismiss(false)
            .asCustom(ShareBottomPopupView(this))
            .show()
    }else{
        XPopup.Builder(this)
            .autoDismiss(false)
            .popupPosition(PopupPosition.Right)
            .asCustom(ShareDrawerPopupView(this))
            .show()
    }
}

class ShareDrawerPopupView(context: Context) : DrawerPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_share_drawer_view
    }

    override fun onCreate() {
        super.onCreate()
        recyclerView.layoutManager = GridLayoutManager(context, 4)
        recyclerView.adapter = ShareAdapter()
        btnCancel.onClick({
            dismiss()
        })
    }
}

class ShareBottomPopupView(context: Context) : BottomPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_share_bottom_view
    }

    override fun onCreate() {
        super.onCreate()
        recyclerView.layoutManager = GridLayoutManager(context, 4)
        recyclerView.adapter = ShareAdapter()
        btnCancel.onClick({
            dismiss()
        })
    }
}

class ShareAdapter : RecyclerView.Adapter<ShareHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShareHolder(parent)

    override fun getItemCount() = Share.values().size

    override fun onBindViewHolder(holder: ShareHolder, position: Int) {
        holder.setData(Share.values()[position])
    }

}

class ShareHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_share, parent, false)
    ) {
    private lateinit var data: Share

    init {
        itemView.onClick({
            data.doShare(itemView.context)
        })
    }

    fun setData(data: Share) {
        this.data = data
        itemView.image.setImageResource(data.drawableRes)
        itemView.title.setText(data.stringRes)
    }

}

enum class Share(val drawableRes: Int, val stringRes: Int) : OnShareAction {
    Facebook(R.drawable.share_facebook, R.string.facebook) {
        override fun doShare(context: Context) {
            showToast()
        }
    },
    Twitter(R.drawable.share_twitter, R.string.twitter) {
        override fun doShare(context: Context) {
            showToast()
        }
    },
    LinkedIn(R.drawable.share_linkedin, R.string.linkedin) {
        override fun doShare(context: Context) {
            showToast()
        }
    },
    GooglePlus(R.drawable.share_google_plus, R.string.google_plus) {
        override fun doShare(context: Context) {
            showToast()
        }
    },
    Instagram(R.drawable.share_instagram, R.string.instagram) {
        override fun doShare(context: Context) {
            showToast()
        }
    },
    Snapchat(R.drawable.share_snapchat, R.string.snapchat) {
        override fun doShare(context: Context) {
            showToast()
        }
    },
    Wechat(R.drawable.share_wechat, R.string.wechat) {
        override fun doShare(context: Context) {
            showToast()
        }
    },
    Moments(R.drawable.share_moments, R.string.moments) {
        override fun doShare(context: Context) {
            showToast()
        }
    },
}

interface OnShareAction {
    fun doShare(context: Context)
}