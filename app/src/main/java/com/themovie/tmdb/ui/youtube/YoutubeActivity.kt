package com.themovie.tmdb.ui.youtube

import android.os.Bundle
import com.themovie.datasource.repository.remote.ApiUrl
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.themovie.tmdb.BuildConfig
import com.themovie.tmdb.R

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        val ytPlayer = findViewById<YouTubePlayerView>(R.id.yt_player)

        ytPlayer.initialize(ApiUrl.YOUTUBE_KEY, this)
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer: YouTubePlayer?,
        p2: Boolean
    ) {
        val bundle = intent.extras
        val key = bundle?.getString("key").toString()
        youTubePlayer?.loadVideo(key)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {

    }
}
