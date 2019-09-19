package com.live

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.adapter.HistoryLiveAdapter
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.model.PageState
import com.live.viewmodel.LivesViewModel
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.smart_refresh_controller.*

class HistoryActivity : BaseActivity() {
    private val livesViewModel: LivesViewModel by lazy {
        LivesViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        topBar.title(R.string.history)
        topBar.back()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = HistoryLiveAdapter()
        recyclerView.adapter = adapter
        livesViewModel.refreshData.observe(this, Observer {
            adapter.refresh(it)
        })
        livesViewModel.loadMoreData.observe(this, Observer {
            adapter.loadMore(it)
        })
        livesViewModel.state.observe(this, Observer {
            when (it) {
                PageState.RefreshLoadMore -> {
                    smartRefresh.finishRefresh(300, true, false)
                }
                PageState.RefreshLoadMoreNoData -> {
                    smartRefresh.finishRefresh(300, true, true)
                }
                PageState.RefreshEmpty -> {
                    smartRefresh.finishRefresh(300, true, true)
                }
                PageState.RefreshError -> {
                    smartRefresh.finishRefresh(300, false, true)
                }
                PageState.LoadError -> {
                    smartRefresh.finishLoadMore(300, false, false)
                }
                PageState.LoadMore -> {
                    smartRefresh.finishLoadMore(300, true, false)
                }
                else -> {
                    smartRefresh.finishLoadMore(300, false, true)
                }
            }
        })
        smartRefresh.autoRefresh()
        smartRefresh.setOnRefreshListener {
            livesViewModel.refresh()
        }
        smartRefresh.setOnLoadMoreListener {
            livesViewModel.loadMore()
            smartRefresh.finishLoadMore()
        }
    }
}