package com.live.permission

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity


fun FragmentActivity.request(vararg permissions: String) {
    ActivityCompat.requestPermissions(this, permissions, 0XFF)
}

fun View.request(
    vararg permissions: String,
    onGranted: () -> Unit,
    onDenied: ((permissions: List<String>) -> Unit)? = null,
    onShowRationale: ((shouldShowRationalePermissions: List<String>) -> Unit)? = null,
    onNeverAskAgain: ((permissions: List<String>) -> Unit)? = null
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
    vararg permission: String,
    onGranted: () -> Unit,
    onDenied: ((permissions: List<String>) -> Unit)? = null,
    onShowRationale: ((shouldShowRationalePermissions: List<String>) -> Unit)? = null,
    onNeverAskAgain: ((permissions: List<String>) -> Unit)? = null
) {
    request(*permission, callback = object : PermissionsCallback {
        override fun onDenied(permissions: List<String>) {
            onDenied?.invoke(permissions)
        }

        override fun onShowRationale(shouldShowRationalePermissions: List<String>) {
            onShowRationale?.invoke(shouldShowRationalePermissions)
        }

        override fun onNeverAskAgain(permissions: List<String>) {
            onNeverAskAgain?.invoke(permissions)
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
        val shouldNotShowRationalePermissions = mutableListOf<String>()
        for (permission in needRequestPermissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission))
                shouldShowRationalePermissions.add(permission)
            else
                shouldNotShowRationalePermissions.add(permission)
        }

        if (shouldShowRationalePermissions.isNotEmpty()) {
            callback.onShowRationale(shouldShowRationalePermissions)
        }


        if (shouldNotShowRationalePermissions.isNotEmpty()) {
            getKtxPermissionFragment(this).also {
                it.setOnPermissionsCallback(callback)
                it.requestPermissionsByFragment(
                    shouldNotShowRationalePermissions.toTypedArray(),
                    0x101
                )
            }
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