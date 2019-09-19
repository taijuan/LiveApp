package com.live.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.live.api.liveAppApi
import com.live.base.model.BaseRes
import com.live.model.LiveDataReq
import com.live.model.LiveDataRes
import com.live.model.PageState
import com.live.utils.logInfo
import com.taijuan.retrofit.Success

class LivesViewModel(private val lifecycleOwner: LifecycleOwner) : ViewModel() {
    private val req: LiveDataReq by lazy {
        LiveDataReq()
    }
    val refreshData = MutableLiveData<List<LiveDataRes>>()
    val loadMoreData = MutableLiveData<List<LiveDataRes>>()
    val state = MutableLiveData<PageState>()

    @MainThread
    fun refresh() {
        req.page = 1
        liveAppApi.selectLivePage(req).observe(lifecycleOwner, Observer {
            when (it) {
                is Success<BaseRes<List<LiveDataRes>>> -> {
                    if (it.body.code != 0) {
                        state.postValue(PageState.RefreshError)
                        return@Observer
                    }
                    refreshData.postValue(it.body.data)
                    when {
                        it.body.data.isNullOrEmpty() -> state.postValue(PageState.RefreshEmpty)
                        it.body.data.size >= req.limit -> state.postValue(PageState.RefreshLoadMore)
                        else -> state.postValue(PageState.RefreshLoadMoreNoData)
                    }
                }
                is Error -> {
                    state.postValue(PageState.RefreshEmpty)
                }
            }
        })
    }

    @MainThread
    fun loadMore() {
        req.page++
        liveAppApi.selectLivePage(req).observe(lifecycleOwner, Observer {
            when (it) {
                is Success<BaseRes<List<LiveDataRes>>> -> {
                    if (it.body.code != 0) {
                        state.postValue(PageState.LoadError)
                        return@Observer
                    }
                    loadMoreData.postValue(it.body.data)
                    when {
                        it.body.data.size >= req.limit -> state.postValue(PageState.LoadMore)
                        else -> state.postValue(PageState.LoadMoreNoData)
                    }
                }
                is Error -> {
                    state.postValue(PageState.LoadError)
                }
            }
        })
    }

    override fun onCleared() {
        "$javaClass ->onCleared".logInfo()
        super.onCleared()
    }
}