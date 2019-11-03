package com.themovie.repos.fromapi

import com.themovie.helper.Constant
import com.themovie.model.online.FetchMainData
import com.themovie.restapi.ApiInterface
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MainRepos
@Inject constructor(private val apiInterface: ApiInterface){

    suspend fun getDataMovie(token: String): FetchMainData? {
        var data: FetchMainData? = null
        try {
            coroutineScope {
                val popular = async(IO) { return@async apiInterface.getPopularMovie(token, Constant.LANGUAGE, 1) }
                val genre = async(IO) { return@async apiInterface.getGenres(token) }
                val upcoming = async(IO) { return@async apiInterface.getUpcomingMovies(token, 1) }
                val discoverTv = async(IO) {
                    return@async apiInterface.getDiscoverTvs(token, Constant.LANGUAGE, Constant.SORTING, 1, "US")
                }
                val discoverMv = async(IO) {
                    return@async apiInterface.getDiscoverMovies(token, Constant.LANGUAGE, Constant.SORTING, 1,
                        "2019", "")
                }
                if(popular.await().isSuccessful && upcoming.await().isSuccessful &&
                    genre.await().isSuccessful && discoverTv.await().isSuccessful && discoverMv.await().isSuccessful){
                    data = FetchMainData(
                        popular.await().body(),
                        genre.await().body(),
                        upcoming.await().body(),
                        discoverTv.await().body(),
                        discoverMv.await().body()
                    )
                }
            }
        } catch (e: Exception){
            throw e
        }
        return data
    }
}