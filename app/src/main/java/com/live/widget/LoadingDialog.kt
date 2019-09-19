package com.live.widget

import androidx.fragment.app.FragmentActivity
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView

fun FragmentActivity.showLoadingDialog(): BasePopupView {
    return XPopup.Builder(this).asLoading().show()
}