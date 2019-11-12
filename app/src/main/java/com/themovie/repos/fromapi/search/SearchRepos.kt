package com.themovie.repos.fromapi.search

import com.themovie.helper.Constant
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.discovertv.TvResponse
import com.themovie.restapi.ApiInterface
import com.themovie.restapi.ApiUrl
import retrofit2.Response
import javax.inject.Inject

class SearchRepos
@Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getSuggestionMoviesSearch(query: String): Response<MoviesResponse> {
        return apiInterface.getSearchMovie(ApiUrl.TOKEN, Constant.LANGUAGE, query, 1)
    }

    suspend fun getSuggestionTvSearch(query: String): Response<TvResponse> {
        return apiInterface.getSearchTv(ApiUrl.TOKEN, Constant.LANGUAGE, query, 1)
    }
}