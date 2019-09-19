package com.live.utils

import java.security.MessageDigest

fun String.toMD5(): String {
    val hash: ByteArray
    try {
        hash = MessageDigest.getInstance("MD5").digest(this.toByteArray(charset("UTF-8")))
    } catch (e: Exception) {
        e.logT()
        return ""
    }
    val hex = StringBuilder(hash.size * 2)
    for (b in hash) {
        val i: Int = b.toInt() and 0xff
        val hexString = Integer.toHexString(i)
        if (hexString.length < 2) {
            hex.append("0$hexString")
        } else {
            hex.append(hexString)
        }
    }
    return hex.toString()
}
