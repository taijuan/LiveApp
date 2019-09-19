package com.live.utils

import com.live.R
import com.live.app.app
import java.util.regex.Pattern


const val REGEX_MOBILE =
    "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[1,8,9]))\\d{8}$"
const val REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"
fun isMatch(regex: String, input: CharSequence?): Boolean {
    return !input.isNullOrEmpty() && Pattern.matches(regex, input)
}

fun isEmail(input: CharSequence): Boolean {
    return isMatch(REGEX_EMAIL, input)
}

fun isMobile(input: CharSequence): Boolean {
    return isMatch(REGEX_MOBILE, input)
}

fun isPassword(input: CharSequence): Boolean {
    return isMatch("^[${app.getString(R.string.regex_password)}]{6,}$", input)
}