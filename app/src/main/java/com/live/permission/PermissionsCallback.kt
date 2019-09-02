package com.live.permission

interface PermissionsCallback {

    fun onGranted()

    fun onDenied(permissions: List<String>)

    fun onShowRationale(request: PermissionRequest)

    fun onNeverAskAgain(permissions: List<String>)
}