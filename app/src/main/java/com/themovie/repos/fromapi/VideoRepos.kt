package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.video.VideoResponse
import com.themovie.restapi.ApiClient
import com.themovie.restapi.ApiInterface
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class VideoRepos
@Inject constructor(private val apiInterface: ApiInterface){

    fun getMovieVideo(movieId: Int, token: String): Single<VideoResponse> {
        return apiInterface.getVideosMovie(movieId, token, Constant.LANGUAGE).subscribeOn(Schedulers.io())
    }

    fun getTvVideo(tvId: Int, token: String): Single<VideoResponse> {
        return apiInterface.getVideosTv(tvId, token, Constant.LANGUAGE).subscribeOn(Schedulers.io())
    }
}