package com.example.videolistplayer.listeners

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.videolistplayer.adapters.VideoListAdapter

class SnapOnScrollListener(
    private val snapHelper: SnapHelper,
    var onSnapPositionChangeListener: OnSnapPositionChangeListener? = null
) : RecyclerView.OnScrollListener() {


    private var snapPosition = RecyclerView.NO_POSITION

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        maybeNotifySnapPositionChange(recyclerView)
    }

    private fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            onSnapPositionChangeListener?.onSnapPositionChange(
                snapPosition,
                recyclerView.findViewHolderForAdapterPosition(snapPosition) as VideoListAdapter.MyViewHolder
            )
            this.snapPosition = snapPosition
        }
    }

    private fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }


    interface OnSnapPositionChangeListener {

        fun onSnapPositionChange(position: Int, viewHolder: VideoListAdapter.MyViewHolder)
    }
}