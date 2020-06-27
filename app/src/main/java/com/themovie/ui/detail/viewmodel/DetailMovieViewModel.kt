package com.themovie.ui.detail.viewmodel

import androidx.lifecycle.*
import com.themovie.model.online.detail.CastResponse
import com.themovie.model.online.detail.DetailMovieResponse
import com.themovie.model.online.detail.ReviewResponse
import com.themovie.model.online.discovermv.MoviesResponse
import com.themovie.model.online.video.VideoResponse
import com.themovie.repos.fromapi.RemoteSource
import com.themovie.restapi.Result
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailMovieViewModel @Inject constructor(
    private val remote: RemoteSource
) : ViewModel() {

    private val _detailMovieRes by lazy { MutableLiveData<Result<DetailMovieResponse>>() }
    private val _creditRes by lazy { MutableLiveData<Result<CastResponse>>() }
    private val _recommendationMovieRes by lazy { MutableLiveData<Result<MoviesResponse>>() }
    private val _trailerRes by lazy { MutableLiveData<Result<VideoResponse>>() }
    private val _reviewsRes by lazy { MutableLiveData<Result<ReviewResponse>>() }

    val detailMovieRes: LiveData<Result<DetailMovieResponse>> get() = _detailMovieRes
    val creditMovieRes: LiveData<Result<CastResponse>> get() = _creditRes
    val recommendationMovieRes: LiveData<Result<MoviesResponse>> get() = _recommendationMovieRes
    val trailerMovieRes: LiveData<Result<VideoResponse>> get() = _trailerRes
    val reviewsMovieRes: LiveData<Result<ReviewResponse>> get() = _reviewsRes

    fun getDetailMovieRequest(filmId: Int){
        viewModelScope.launch {
            val detailRes = async(IO) { remote.getDetailMovie(filmId) }
            val creditsRes = async(IO) { remote.getCreditMovie(filmId) }
            val recomRes = async(IO) { remote.getRecommendationMovie(filmId) }
            val trailer = async(IO) { remote.getTrailerMovie(filmId) }
            val reviews = async(IO) { remote.getReviewMovie(filmId) }

            _detailMovieRes.value = detailRes.await()
            _creditRes.value = creditsRes.await()
            _recommendationMovieRes.value = recomRes.await()
            _trailerRes.value = trailer.await()
            _reviewsRes.value = reviews.await()

        }
    }
}