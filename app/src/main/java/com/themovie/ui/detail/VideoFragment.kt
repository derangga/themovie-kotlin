package com.themovie.ui.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.video.VideoResponse
import com.themovie.restapi.ApiUrl
import com.themovie.ui.detail.adapter.VideoAdapter
import kotlinx.android.synthetic.main.fragment_video.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class VideoFragment : BaseFragment() {

    private lateinit var videoViewModel: VideoViewModel
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        videoViewModel = ViewModelProviders.of(this).get(VideoViewModel::class.java)
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onMain(savedInstanceState: Bundle?) {
        val videoFor = getBundle()?.getString("video")
        setupRecycler()
        if(videoFor.equals(Constant.MOVIE)){
            getVideoMovie()
        } else getVideoTv()
        getLoadDataStatus()
    }

    private fun setupRecycler(){
        videoAdapter = VideoAdapter()
        dt_video.layoutManager = LinearLayoutManager(context)
        dt_video.adapter = videoAdapter
    }

    private fun getVideoMovie(){
        videoViewModel.getVideoMovie(getBundle()!!.getInt("filmId"), ApiUrl.TOKEN).observe(
            this, Observer<VideoResponse> {
                videoAdapter.submitList(it.videos)
            }
        )
    }

    private fun getVideoTv(){
        videoViewModel.getVideoTv(getBundle()!!.getInt("filmId"), ApiUrl.TOKEN).observe(
            this, Observer<VideoResponse> {
                videoAdapter.submitList(it.videos)
            }
        )
    }

    private fun getLoadDataStatus(){
        videoViewModel.getLoadDataStatus().observe(
            this, Observer<LoadDataState> {
                if(it == LoadDataState.LOADED) hideLoading()
                else showNetworkError()
            }
        )
    }

    private fun showLoading(){
        shimmer_video.visibility = View.VISIBLE
        dt_no_internet.visibility = View.GONE
    }

    private fun hideLoading(){
        shimmer_video.visibility = View.GONE
        dt_no_internet.visibility = View.GONE
    }

    private fun showNetworkError(){
        shimmer_video.visibility = View.INVISIBLE
        dt_no_internet.visibility = View.VISIBLE
    }

}
