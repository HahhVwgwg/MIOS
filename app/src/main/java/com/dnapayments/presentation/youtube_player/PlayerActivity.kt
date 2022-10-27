package com.dnapayments.presentation.youtube_player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dnapayments.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class PlayerActivity : AppCompatActivity() {

    lateinit var videoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        videoId = intent.getStringExtra(KEY_VIDEO_ID) ?: ""

        val playerView = findViewById<YouTubePlayerView>(R.id.youTubePlayerView)

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
        playerView.enterFullScreen()
    }

    companion object {
        const val KEY_VIDEO_ID = "KEY_VIDEO_ID"
    }
}