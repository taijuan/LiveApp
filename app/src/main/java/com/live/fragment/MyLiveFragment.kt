package com.live.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.live.R
import com.live.adapter.MyLiveAdapter
import com.live.base.BaseFragment
import com.live.model.LiveData
import kotlinx.android.synthetic.main.smart_refresh_controller.*

class MyLiveFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.smart_refresh_controller, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        val adapter = MyLiveAdapter()
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