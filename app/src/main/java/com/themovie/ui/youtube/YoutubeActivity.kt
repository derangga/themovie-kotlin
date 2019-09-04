package com.themovie.ui.youtube

import android.os.Bundle
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.themovie.R
import com.themovie.restapi.ApiUrl
import kotlinx.android.synthetic.main.activity_youtube.*

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        yt_player.initialize(ApiUrl.YT_API, this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, p2: Boolean) {
        val bundle = intent.extras
        val key = bundle?.getString("key").toString()
        youTubePlayer?.loadVideo(key)
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

    }
}
