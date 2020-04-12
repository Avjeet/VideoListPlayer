package com.example.videolistplayer.controller

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import com.example.videolistplayer.R
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import java.io.File

object PlayerController {

    @JvmStatic
    private var cache: SimpleCache? = null

    /**
     * Method to initialize simple exoPlayer instance and
     * and set repeat mode to off.
     * @return SimpleExoPlayer instance
     */
    fun getSimpleExoPlayer(context: Context): SimpleExoPlayer {

        val simpleExoPlayer = SimpleExoPlayer.Builder(context).build()

        simpleExoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        return simpleExoPlayer
    }

    /**
     * Method to inflate the player view with default ui controller.
     * @return PlayerView instance
     */
    fun getPlayerView(layoutInflater: LayoutInflater): PlayerView {
        val playerView = layoutInflater.inflate(R.layout.player_view, null) as PlayerView
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
        return playerView
    }

    /**
     * Method which takes input the list of video urls and
     * prepares a concatenating media source list to be played by exoplayer.
     *
     * <p>
     *     Wrapping defaultDataSourceFactory by CacheDataSourceFactory will
     *     facilitate the caching of the media provided by the media source.
     * </p>
     *
     * @param context
     * @param urls
     * @return concatenatingMediaSource
     */
    fun getMediaSourceList(context: Context, urls: List<String>): ConcatenatingMediaSource {

        val concatenatingMediaSource = ConcatenatingMediaSource()

        val dataSourceFactory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, "VideoListPlayer")
        )

        val cacheDataSourceFactory = CacheDataSourceFactory(
            getCache(
                context
            ), dataSourceFactory)


        for (url in urls) {
            val uri = Uri.parse(url)
            val videoSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(uri)

            concatenatingMediaSource.addMediaSource(videoSource)
        }

        return concatenatingMediaSource
    }


    /**
     * Method to return directory where cached media data will be stored
     * @return Cache instance
     */
    private fun getCache(context: Context): Cache {

        if(cache == null){
            val cacheDirectory = File(context.externalCacheDir, "downloads")

            val evictor = LeastRecentlyUsedCacheEvictor((100 * 1024 * 1024).toLong())
            val databaseProvider: DatabaseProvider = ExoDatabaseProvider(context)
            cache = SimpleCache(cacheDirectory, evictor, databaseProvider)
        }

        return cache as SimpleCache
    }
}