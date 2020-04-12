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

    /**
     * This method will be called after the scroll listener is added to recycler view
     * with dx = 0 && dy = 0.
     *
     * <p>
     *     This method will facilitate the attaching
     *     of playerView in the first child of the recycler
     *     view by notifying recyclerView with position = 0
     *     as current snap position returned by getSnapPosition()
     *     will be equal to 0.
     * </p>
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if(dx==0 && dy ==0){
            maybeNotifySnapPositionChange(recyclerView)
        }
    }

    /**
     * Custom method which will notify recycler view of child snap
     * if the new snapPosition is not equal to the initial snapPosition
     * to avoid repeated call to same child of recyclerView
     * @param recyclerView
     */
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

    /**
     * @param recyclerView
     * @return position of currently visible
     * child view of recycler view after snap
     */
    private fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }


    /**
     * Custom interface which will be implemented by Main activity
     * to execute the code when snap position is changed
     */
    interface OnSnapPositionChangeListener {

        fun onSnapPositionChange(position: Int, viewHolder: VideoListAdapter.MyViewHolder)
    }
}