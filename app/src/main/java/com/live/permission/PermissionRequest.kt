package com.live.permission

/**
 * Created by luyao
 * on 2019/6/21 15:28
 */
data class PermissionRequest(
    val permissionFragment: PermissionFragment,
    val permissions: List<String>,
    val requestCode: Int
)