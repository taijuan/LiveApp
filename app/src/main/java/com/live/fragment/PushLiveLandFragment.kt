package com.live.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alivc.live.pusher.AlivcPreviewOrientationEnum
import com.live.PushLiveActivity
import com.live.R
import com.live.base.BaseFragment
import com.live.utils.onClick
import com.live.utils.pop
import kotlinx.android.synthetic.main.push_live_land_controller.*

class PushLiveLandFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.push_live_land_controller, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSwitchScreen.onClick({
            (activity as PushLiveActivity).setOrientation(AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT)
        })
        btnAction.onClick({
            (activity as PushLiveActivity).startLive()
        })
        btnSwitchCamera.onClick({
            (activity as PushLiveActivity).switchCamera {
                btnFlash.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        btnFlash.onClick({
            (activity as PushLiveActivity).switchFlash()
        })
        btnClose.onClick({
            (activity as PushLiveActivity).pop()
        })
    }
}