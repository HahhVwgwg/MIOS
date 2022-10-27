package com.dnapayments.presentation.details

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentDetailsBinding
import com.dnapayments.presentation.youtube_player.PlayerActivity
import com.dnapayments.presentation.youtube_player.PlayerActivity.Companion.KEY_VIDEO_ID
import com.dnapayments.utils.Constants
import com.dnapayments.utils.base.BaseBindingFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment :
    BaseBindingFragment<FragmentDetailsBinding, DetailsViewModel>(R.layout.fragment_details) {
    override val vm: DetailsViewModel by viewModel()

    lateinit var youTubePlayerView: YouTubePlayerView
    var videoUrl: String = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun initViews(savedInstanceState: Bundle?) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        videoUrl = arguments?.getString(Constants.VIDEO_URL) ?: ""
        val lessonId = arguments?.getInt(Constants.LESSON_ID) ?: -1
        val passed = arguments?.getBoolean(Constants.PASSED) ?: false

        binding?.run {
            youTubePlayerView = playerView
            viewModel = vm
            vm.title.set(arguments?.getString(Constants.COURSE_TITLE) ?: "")

            back.setOnClickListener {
                onBackPressed()
            }
            passQuiz.setOnClickListener {
                findNavController().navigate(R.id.action_details_to_quiz, Bundle().apply {
                    putInt(Constants.LESSON_ID, lessonId)
                    putBoolean(Constants.PASSED, passed)
                })
            }
            if (videoUrl[videoUrl.length - 1] == '?') {
                videoUrl = videoUrl.substring(0, videoUrl.length - 1)
            }
            videoUrl = videoUrl.split("/").toMutableList().lastOrNull() ?: ""

            videoLayout.setOnClickListener {
                openPlayer(videoUrl)
            }
            viewClick.setOnClickListener {
                openPlayer(videoUrl)
            }
            youTubePlayerView.isClickable = false
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoUrl, 0f)
                }
            })
            lifecycle.addObserver(youTubePlayerView)
        }

    }

    private fun openPlayer(videoUrl: String) {
        val intent = Intent(requireActivity(), PlayerActivity::class.java)
        intent.putExtra(KEY_VIDEO_ID, videoUrl)
        requireActivity().startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
    }
}