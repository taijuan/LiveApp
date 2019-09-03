package com.live.permission

interface PermissionsCallback {

    fun onGranted()

    fun onDenied(denied: List<String>)

    fun onShowRationale(shouldShowRationale: List<String>)

    fun onNeverAskAgain(neverAskAgain: List<String>)
}