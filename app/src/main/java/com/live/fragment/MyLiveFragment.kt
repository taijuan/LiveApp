package com.live.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.R
import com.live.adapter.MyLiveAdapter
import com.live.base.BaseFragment
import com.live.model.PageState
import com.live.viewmodel.LivesViewModel
import kotlinx.android.synthetic.main.smart_refresh_controller.*

class MyLiveFragment : BaseFragment() {
    private val livesViewModel: LivesViewModel by lazy {
        LivesViewModel(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.smart_refresh_controller, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val adapter = MyLiveAdapter()
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