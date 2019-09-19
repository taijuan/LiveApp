package com.live.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.live.R
import kotlinx.android.synthetic.main.fragment_zxing.*

typealias OnCallback = (String) -> Unit

class ZXingFragment : Fragment(), QRCodeView.Delegate {
    private lateinit var onCallback: OnCallback
    fun setOnCallback(onCallback: OnCallback) {
        this.onCallback = onCallback
    }

    override fun onScanQRCodeSuccess(result: String?) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show()
        this.onCallback.invoke(result ?: "")
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {

    }

    override fun onScanQRCodeOpenCameraError() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_zxing, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        zxingview.setDelegate(this)
    }

    override fun onStart() {
        zxingview.startCamera()
        zxingview.startSpotAndShowRect()
        super.onStart()
    }

    override fun onStop() {
        zxingview.stopSpotAndHiddenRect()
        zxingview.stopCamera()
        super.onStop()
    }
}

fun FragmentActivity.getZxFragment(): ZXingFragment {
    var fragment =
        supportFragmentManager.findFragmentByTag(ZXingFragment::class.java.name)
    if (fragment == null) {
        fragment = ZXingFragment()
    }
    return fragment as ZXingFragment
}

fun FragmentActivity.requestZXing(onCallback: OnCallback) {
    val fragment = getZxFragment()
    fragment.setOnCallback(onCallback)
    if (!fragment.isAdded) {
        supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, ZXingFragment::class.java.name)
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commitNow()
    }
}

fun FragmentActivity.onBackPressZxing(): Boolean {
    val fragment = getZxFragment()
    return if (!fragment.isAdded) {
        true
    } else {
        supportFragmentManager
            .beginTransaction()
            .remove(fragment)
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .commitNow()
        false
    }
}