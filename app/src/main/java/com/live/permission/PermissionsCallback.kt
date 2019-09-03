package com.live.permission

interface PermissionsCallback {

    fun onGranted()

    fun onDenied(permissions: List<String>)

    fun onShowRationale(shouldShowRationalePermissions: List<String>)

    fun onNeverAskAgain(permissions: List<String>)
}