package com.live.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.live.*
import com.live.base.BaseFragment
import com.live.model.UserRes
import com.live.utils.loadImageCircleCrop
import com.live.utils.onClick
import com.live.utils.push
import com.live.viewmodel.getUser
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : BaseFragment() {
    private val user: UserRes by lazy {
        getUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_me, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgHead.loadImageCircleCrop(user.headImage)
        tvName.text = user.nickname
        tvJob.text = user.nickname
        btnProfile.onClick({
            push(ProfileActivity::class.java)
        })
        tvHistory.onClick({
            push(HistoryActivity::class.java)
        })
        tvSettings.onClick({
            push(SettingsActivity::class.java)
        })
        tvAbout.onClick({
            push(AboutActivity::class.java)
            closeDrawer()
        })
    }

    /**
     * 关闭左侧抽屉
     */
    private fun closeDrawer() {
        (activity as? HomeActivity)?.closeDrawer()
    }
}