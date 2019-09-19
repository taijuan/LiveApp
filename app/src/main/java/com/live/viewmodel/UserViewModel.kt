package com.live.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.live.api.liveAppApi
import com.live.base.model.BaseRes
import com.live.model.UserReq
import com.live.model.UserRes
import com.live.utils.SPUtils
import com.live.utils.isMobile
import com.live.utils.isPassword
import com.live.utils.showToast
import com.taijuan.retrofit.Error
import com.taijuan.retrofit.Success

class UserViewModel(private val lifecycleOwner: LifecycleOwner) : ViewModel() {

    fun login(userReq: UserReq, onSuccess: () -> Unit, onDone: () -> Unit) {
        liveAppApi.logByPwd(userReq).observe(lifecycleOwner, Observer {
            onDone.invoke()
            when (it) {
                is Success<BaseRes<UserRes>> -> {
                    if (it.body.code == 0) {
                        it.body.data.save()
                        onSuccess.invoke()
                    } else {
                        it.body.msg.showToast()
                    }
                }
                is Error -> {
                    it.errorMsg.showToast()
                }
            }
        })
    }

    private fun UserRes.save() {
        SPUtils().put("user", Gson().toJson(this))
    }


}

fun getUser(): UserRes {
    val userStr = SPUtils().getString("user", "")
    return Gson().fromJson(userStr, UserRes::class.java) ?: UserRes()
}

fun clearUser() {
    SPUtils().remove("user")
}