package com.example.videolistplayer.listeners

import com.google.android.exoplayer2.Player

/**
 * Custom class which overrides the ExoPLayer event listener class
 * to
 */
class VideoPlayerListener(private val playListener: OnNextVideoPlayListener) : Player.EventListener {

    override fun onPositionDiscontinuity(reason: Int){
        super.onPositionDiscontinuity(reason)

        if (reason == Player.DISCONTINUITY_REASON_PERIOD_TRANSITION) {
            playListener.onNextVideoPlay()
        }
    }


    /**
     * Custom interface to notify that the next video has started playing
     */
    interface OnNextVideoPlayListener{
        fun onNextVideoPlay()
    }
}
