package com.live

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import com.alivc.live.pusher.AlivcResolutionEnum
import com.live.app.app
import com.live.base.BaseActivity
import com.live.base.add
import com.live.fragment.AllLiveFragment
import com.live.fragment.MyLiveFragment
import com.live.utils.logE
import com.live.utils.onClick
import com.live.widget.QMUMenuPopup
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        AlivcResolutionEnum.RESOLUTION_SELFDEFINE.setSelfDefineResolution(
            QMUIDisplayHelper.getScreenWidth(app), QMUIDisplayHelper.getScreenHeight(app)
        )
        QMUIDisplayHelper.getScreenWidth(app).logE("width: ")
        QMUIDisplayHelper.getScreenHeight(app).logE("height: ")
        topBar.addLeftImageButton(R.drawable.hk_home, R.integer.id_home).onClick({
            drawer.openDrawer(GravityCompat.START, true)
        })
        topBar.add()?.onClick({
            QMUMenuPopup(this).apply {
                create()
                show(popAnchorView)
            }
        })
        viewPager.adapter = object : FragmentStatePagerAdapter(
            supportFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int) = when (position) {
                0 -> MyLiveFragment()
                else -> AllLiveFragment()
            }

            override fun getCount() = 2

        }
        tabHome.setupWithViewPager(viewPager)
        tabHome.getTabAt(0)?.setCustomView(R.layout.view_tab_left)
        tabHome.getTabAt(1)?.setCustomView(R.layout.view_tab_right)
    }

    /**
     * 关闭左侧抽屉
     */
    fun closeDrawer() {
        drawer.closeDrawer(GravityCompat.START, true)
    }
}
