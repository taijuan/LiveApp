package com.live

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.adapter.HistoryLiveAdapter
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.title
import com.live.model.LiveData
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.smart_refresh_controller.*

class HistoryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        topBar.title(R.string.history)
        topBar.back()
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = HistoryLiveAdapter()
        recyclerView.adapter = adapter
        smartRefresh.autoRefresh()
        smartRefresh.setOnRefreshListener {
            adapter.refresh(
                listOf(
                    LiveData(),
                    LiveData(),
                    LiveData(),
                    LiveData(),
                    LiveData(),
                    LiveData()
                )
            )
            smartRefresh.finishRefresh()
        }
        smartRefresh.setOnLoadMoreListener {
            adapter.loadMore(
                listOf(
                    LiveData(),
                    LiveData(),
                    LiveData(),
                    LiveData(),
                    LiveData(),
                    LiveData()
                )
            )
            smartRefresh.finishLoadMore()
        }
    }
}