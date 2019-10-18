package com.themovie.ui.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.themovie.MyApplication

import com.themovie.R
import com.themovie.base.BaseFragment
import com.themovie.databinding.FragmentVideoBinding
import com.themovie.helper.Constant
import com.themovie.helper.LoadDataState
import com.themovie.model.online.video.VideoResponse
import com.themovie.model.online.video.Videos
import com.themovie.ui.detail.adapter.VideoAdapter
import com.themovie.ui.detail.viewmodel.VideoViewModel
import com.themovie.ui.detail.viewmodel.VideoViewModelFactory
import com.themovie.ui.youtube.YoutubeActivity
import kotlinx.android.synthetic.main.fragment_video.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 *
 */
class VideoFragment : BaseFragment() {

    @Inject lateinit var viewModelFactory: VideoViewModelFactory
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var videoFor: String
    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false)
        val view: View = binding.root
        (activity?.application as MyApplication).getAppComponent().inject(this)
        videoViewModel = ViewModelProvider(this, viewModelFactory).get(VideoViewModel::class.java)
        binding.apply {
            vm = videoViewModel
            lifecycleOwner = this@VideoFragment
        }

        return view
    }

    override fun onMain(savedInstanceState: Bundle?) {
        videoFor = getBundle()?.getString("video").toString()
        setupRecycler()
        if(videoFor.equals(Constant.MOVIE)){
            getVideoMovie()
        } else getVideoTv()
        getLoadDataStatus()
    }

    private fun setupRecycler(){
        videoAdapter = VideoAdapter()
        binding.apply {
            dtVideo.layoutManager = LinearLayoutManager(context)
            dtVideo.adapter = videoAdapter
        }


        videoAdapter.setOnClickAdapter(object: VideoAdapter.OnClickAdapterListener{
            override fun onClick(view: View?, videos: Videos) {
                val bundle = Bundle()
                bundle.putString("key", videos.key)
                changeActivity(bundle, YoutubeActivity::class.java)
            }
        })
    }

    private fun getVideoMovie(){
        videoViewModel.getVideoMovie(getBundle()!!.getInt("filmId")).observe(
            this, Observer<VideoResponse> {
                videoAdapter.submitList(it.videos)
            }
        )
    }

    private fun getVideoTv(){
        videoViewModel.getVideoTv(getBundle()!!.getInt("filmId")).observe(
            this, Observer<VideoResponse> {
                videoAdapter.submitList(it.videos)
            }
        )
    }

    private fun getLoadDataStatus(){
        videoViewModel.getLoadDataStatus().observe(
            this, Observer<LoadDataState> {
                if(it == LoadDataState.LOADED) hideLoading()
                else {
                    showNetworkError()
                    binding.dtRetry.setOnClickListener {
                        showLoading()
                        if(videoFor.equals(Constant.MOVIE)){
                            getVideoMovie()
                        } else getVideoTv()
                    }
                }
            }
        )
    }

    private fun showLoading(){
        binding.apply {
            shimmerVideo.visibility = View.VISIBLE
            dtNoInternet.visibility = View.GONE
        }
    }

    private fun hideLoading(){
        binding.apply {
            shimmerVideo.visibility = View.GONE
            dtNoInternet.visibility = View.GONE
        }
    }

    private fun showNetworkError(){
        binding.apply {
            shimmerVideo.visibility = View.INVISIBLE
            dtNoInternet.visibility = View.VISIBLE
        }

    }

}
