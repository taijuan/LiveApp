package com.live.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.live.base.BaseActivity

fun <T : BaseActivity> Activity.push(clazz: Class<T>, data: Bundle = Bundle.EMPTY) {
    this.startActivity(Intent(this, clazz).apply {
        putExtras(data)
    })
}

fun <T : BaseActivity> Activity.pushForResult(
    clazz: Class<T>,
    requestCode: Int,
    data: Bundle = Bundle.EMPTY
) {
    this.startActivityForResult(Intent(this, clazz).apply {
        putExtras(data)
    }, requestCode)
}

fun <T : BaseActivity> Fragment.push(clazz: Class<T>, data: Bundle = Bundle.EMPTY) {
    this.activity?.push(clazz, data)
}

fun <T : BaseActivity> View.push(clazz: Class<T>, data: Bundle = Bundle.EMPTY) {
    (this.context as? Activity)?.push(clazz, data)
}

fun <T : BaseActivity> AppCompatActivity.pushAndPop(clazz: Class<T>, data: Bundle = Bundle.EMPTY) {
    push(clazz, data)
    pop()
}

fun Activity.pop() {
    onBackPressed()
}

fun View.pop() {
    (this.context as? Activity)?.pop()
}