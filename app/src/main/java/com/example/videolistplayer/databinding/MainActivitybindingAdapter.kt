package com.example.videolistplayer.databinding

import android.view.LayoutInflater
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.videolistplayer.listeners.SnapOnScrollListener
import com.example.videolistplayer.adapters.VideoListAdapter

@BindingAdapter("urlList")
fun RecyclerView.seturlList(urls: List<String>) {
    with(this) {
        layoutManager =
            layoutManager ?: LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        adapter = adapter ?: VideoListAdapter(
            urls,
            LayoutInflater.from(this.context)
        )
    }
}

@BindingAdapter("snapHelper", "snapPositionChangeListener")
fun RecyclerView.attachSnapHelperWithListener(
    snapHelper: SnapHelper,
    snapPositionChangeListener: SnapOnScrollListener.OnSnapPositionChangeListener
) {

    val snapOnScrollListener = SnapOnScrollListener(
        snapHelper,
        snapPositionChangeListener
    )
    snapHelper.attachToRecyclerView(this)
    addOnScrollListener(snapOnScrollListener)
}
