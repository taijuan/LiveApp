package com.live.utils

import com.live.app.app
import com.ut.device.UTDevice

val uuid: String by lazy {
    val uuid = UTDevice.getUtdid(app)
    uuid.logE("UUID：")
    uuid
}
