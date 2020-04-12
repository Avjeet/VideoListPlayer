package com.example.videolistplayer.listeners

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

class VideoPlayerListener(): Player.EventListener{

    override fun onPositionDiscontinuity(reason: Int){
        super.onPositionDiscontinuity(reason)
    }
}
