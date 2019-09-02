package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.video.VideoResponse
import com.themovie.restapi.ApiClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class VideoRepos {

    fun getMovieVideo(movieId: Int, token: String): Single<VideoResponse> {
        return ApiClient.getApiBuilder().getVideosMovie(movieId, token, Constant.LANGUAGE).subscribeOn(Schedulers.io())
    }

    fun getTvVideo(tvId: Int, token: String): Single<VideoResponse> {
        return ApiClient.getApiBuilder().getVideosTv(tvId, token, Constant.LANGUAGE).subscribeOn(Schedulers.io())
    }
}