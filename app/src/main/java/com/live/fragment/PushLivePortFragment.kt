package com.live.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.live.PushLiveActivity
import com.live.R
import com.live.base.BaseFragment
import com.live.utils.HandlerUtils
import com.live.utils.logE
import com.live.utils.onClick
import com.live.utils.pop
import kotlinx.android.synthetic.main.push_live_port_controller.*

class PushLivePortFragment : BaseFragment() {
    private val handlerUtils: HandlerUtils by lazy {
        HandlerUtils(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.push_live_port_controller, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as PushLiveActivity).run {
            btnX1.onClick({
                setZoom()
            })
            btnAction.setImageResource(if (isStartLive()) R.drawable.hk_push_live_stop else R.drawable.hk_push_live_start)
            btnAction.onClick({
                switchLive {
                    btnAction.setImageResource(if (it) R.drawable.hk_push_live_stop else R.drawable.hk_push_live_start)
                }
            })
            btnSwitchCamera.onClick({
                switchCamera {
                    btnFlash.visibility = if (it) View.VISIBLE else View.GONE
                }
            })
            btnFlash.visibility =
                if (isSupportFlash()) View.VISIBLE else View.GONE
            btnFlash.onClick({
                switchFlash()
            })
            btnClose.onClick({
                pop()
            })
            tvDestination.text = "深圳"
            timeText()
        }
    }

    private fun PushLiveActivity.timeText() {
        time().logE()
        handlerUtils.postDelayed({
            tvTime?.text = time()
            timeText()
        }, 1000)
    }
}