package com.live.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.request(vararg permissions: String) {
    getKtxPermissionFragment(this).requestPermissionsByFragment(
        permissions.distinct().toTypedArray(),
        0x101
    )
}

fun View.request(
    vararg permissions: String,
    onGranted: () -> Unit,
    onDenied: ((denied: List<String>, permissions: List<String>) -> Unit)? = null,
    onShowRationale: ((shouldShowRationale: List<String>, deniedPermissions: List<String>) -> Unit)? = null,
    onNeverAskAgain: ((neverAskAgain: List<String>, permissions: List<String>) -> Unit)? = null
) {
    (context as? FragmentActivity)?.request(
        *permissions,
        onGranted = onGranted,
        onDenied = onDenied,
        onShowRationale = onShowRationale,
        onNeverAskAgain = onNeverAskAgain
    )
}

fun FragmentActivity.request(
    vararg permissions: String,
    onGranted: () -> Unit,
    onDenied: ((denied: List<String>, permissions: List<String>) -> Unit)? = null,
    onShowRationale: ((shouldShowRationale: List<String>, permissions: List<String>) -> Unit)? = null,
    onNeverAskAgain: ((neverAskAgain: List<String>, permissions: List<String>) -> Unit)? = null
) {
    request(*permissions, callback = object : PermissionsCallback {
        override fun onDenied(denied: List<String>) {
            onDenied?.invoke(denied, permissions.toList())
        }

        override fun onShowRationale(shouldShowRationale: List<String>) {
            onShowRationale?.invoke(shouldShowRationale, permissions.toList())
        }

        override fun onNeverAskAgain(neverAskAgain: List<String>) {
            onNeverAskAgain?.invoke(neverAskAgain, permissions.toList())
        }

        override fun onGranted() {
            onGranted()
        }

    })
}

fun FragmentActivity.request(vararg permissions: String, callback: PermissionsCallback) {

    val needRequestPermissions = permissions.filter { !isGranted(it) }

    if (needRequestPermissions.isEmpty()) {
        callback.onGranted()
    } else {
        val shouldShowRationalePermissions = mutableListOf<String>()
        for (permission in needRequestPermissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                shouldShowRationalePermissions.add(permission)
            }
        }

        if (shouldShowRationalePermissions.isNotEmpty()) {
            callback.onShowRationale(shouldShowRationalePermissions)
        }
        getKtxPermissionFragment(this).also {
            it.setOnPermissionsCallback(callback)
            it.requestPermissionsByFragment(
                needRequestPermissions.toTypedArray(),
                0x101
            )
        }
    }
}

private fun getKtxPermissionFragment(activity: FragmentActivity): PermissionFragment {
    var fragment =
        activity.supportFragmentManager.findFragmentByTag(PermissionFragment::class.java.name)
    if (fragment == null) {
        fragment = PermissionFragment()
        activity.supportFragmentManager.beginTransaction()
            .add(fragment, PermissionFragment::class.java.name).commitNow()
    }
    return fragment as PermissionFragment
}


fun Activity.isGranted(permission: String): Boolean {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
}