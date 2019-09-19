package com.live.utils

import android.widget.Toast
import com.live.app.app

fun Any.showToast() {
    Toast.makeText(app, "$this", Toast.LENGTH_SHORT).show()
}