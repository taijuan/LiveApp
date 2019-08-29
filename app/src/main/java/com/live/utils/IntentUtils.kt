package com.live.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.live.base.BaseActivity

fun <T : BaseActivity> Activity.push(clazz: Class<T>) {
    this.startActivity(Intent(this, clazz))
}

fun <T : BaseActivity> Fragment.push(clazz: Class<T>) {
    this.activity?.push(clazz)
}

fun <T : BaseActivity> View.push(clazz: Class<T>) {
    (this.context as? Activity)?.push(clazz)
}

fun <T : BaseActivity> AppCompatActivity.pushAndPop(clazz: Class<T>) {
    this.startActivity(Intent(this, clazz))
    this.pop()
}

fun Activity.pop() {
    this.finish()
}

fun View.pop() {
    (this.context as? Activity)?.onBackPressed()
}