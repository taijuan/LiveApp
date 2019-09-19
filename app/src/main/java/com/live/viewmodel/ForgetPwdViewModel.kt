package com.live.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.live.api.liveAppApi
import com.live.model.RegReq
import com.live.utils.showToast
import com.taijuan.retrofit.Error
import com.taijuan.retrofit.Success

class ForgetPwdViewModel(private val lifecycleOwner: LifecycleOwner) : ViewModel() {
    fun getForgetPwdCode(regReq: RegReq, onDone: () -> Unit) {
        liveAppApi.getForgetPwdCode(regReq).observe(lifecycleOwner, Observer {
            onDone.invoke()
            when (it) {
                is Success -> {
                    if (it.body.code != 0) {
                        it.body.msg.showToast()
                    }
                }
                is Error -> {
                    it.errorMsg.showToast()
                }
            }
        })
    }

    fun forgetPwdByCode(regReq: RegReq, onSuccess: () -> Unit, onDone: () -> Unit) {
        liveAppApi.forgetPwdByCode(regReq).observe(lifecycleOwner, Observer {
            onDone.invoke()
            when (it) {
                is Success -> {
                    if (it.body.code != 0) {
                        it.body.msg.showToast()
                    } else {
                        onSuccess.invoke()
                    }
                }
                is Error -> {
                    it.errorMsg.showToast()
                }
            }
        })
    }
}