package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.video.VideoResponse
import com.themovie.restapi.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class VideoRepos
@Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getMovieVideo(movieId: Int, token: String): Response<VideoResponse> {
        return apiInterface.getVideosMovie(movieId, token, Constant.LANGUAGE)
    }

    suspend fun getTvVideo(tvId: Int, token: String): Response<VideoResponse> {
        return apiInterface.getVideosTv(tvId, token, Constant.LANGUAGE)
    }
}