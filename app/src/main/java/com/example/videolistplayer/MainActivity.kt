package com.example.videolistplayer

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.videolistplayer.adapters.VideoListAdapter
import com.example.videolistplayer.controller.PlayerController
import com.example.videolistplayer.databinding.ActivityMainBinding
import com.example.videolistplayer.listeners.SnapOnScrollListener
import com.example.videolistplayer.listeners.VideoPlayerListener
import com.example.videolistplayer.models.VideoRepo
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : AppCompatActivity(), SnapOnScrollListener.OnSnapPositionChangeListener,
    VideoPlayerListener.OnNextVideoPlayListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupExoPlayer()

        with(binding){
            urls =  VideoRepo.URLS
            snapHelper = PagerSnapHelper()
            snapOnPositionChangeListener = this@MainActivity
        }
    }

    /**
     * Method to :
     * - setup the exoPlayer instance
     * - prepare the concatenatingMediaSource list of all the video urls
     * - attach simpleExoPlayer to player view
     */
    private fun setupExoPlayer() {
        simpleExoPlayer = PlayerController.getSimpleExoPlayer(this).apply {
            prepare(PlayerController.getMediaSourceList(this@MainActivity, VideoRepo.URLS))
            addListener(VideoPlayerListener(this@MainActivity))
        }

        playerView = PlayerController.getPlayerView(layoutInflater).apply {
            player = simpleExoPlayer
        }
    }


    /**
     * Overridden method of SnapOnScrollListener class
     * which will be called whenever the snap position.
     *
     * <p>
     *     This method
     *     - removes the playerView from the previous child and add the playerView to the current child
     *     - seek to current index in exoPlayer
     *     - play the video
     *</p>
     */
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

    override fun onNextVideoPlay() {
        binding.recyclerView.smoothScrollToPosition(simpleExoPlayer.currentWindowIndex)
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

}
