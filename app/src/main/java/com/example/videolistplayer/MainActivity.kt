package com.example.videolistplayer

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.videolistplayer.adapters.VideoListAdapter
import com.example.videolistplayer.controller.PlayerController
import com.example.videolistplayer.databinding.ActivityMainBinding
import com.example.videolistplayer.listeners.SnapOnScrollListener
import com.example.videolistplayer.models.VideoRepo
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : AppCompatActivity(), SnapOnScrollListener.OnSnapPositionChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupExoPlayer()

        with(binding){
            urls =  VideoRepo.URLS
            snapHelper = LinearSnapHelper()
            snapOnPositionChangeListener = this@MainActivity
        }
    }

    private fun setupExoPlayer() {
        simpleExoPlayer = PlayerController.getExoplayerInstance(this).apply {
            prepare(PlayerController.getMediaSourceList(this@MainActivity, VideoRepo.URLS))
            addListener()
        }

        playerView = PlayerController.getPlayerView(layoutInflater).apply {
            player = simpleExoPlayer
        }
    }

    override fun onSnapPositionChange(position: Int, viewHolder: VideoListAdapter.MyViewHolder) {

        simpleExoPlayer.playWhenReady = false

        playerView.parent?.let {
            (it as ViewGroup).removeView(playerView)
        }

        viewHolder.binding?.mediaContainer?.addView(playerView)

        simpleExoPlayer.apply {
            seekTo(position, 0L)
            playWhenReady = true
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        simpleExoPlayer.release()
    }
}
