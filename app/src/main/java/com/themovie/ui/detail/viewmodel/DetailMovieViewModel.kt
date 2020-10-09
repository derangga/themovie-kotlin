package com.themovie.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.domain.entities.remote.*
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.DataList
import com.aldebaran.domain.repository.remote.MovieRemoteSource

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailMovieViewModel @ViewModelInject constructor(
    private val remote: MovieRemoteSource
) : ViewModel() {

    private val _detailMovieRes by lazy { MutableLiveData<Result<DetailMovieResponse>>() }
    private val _creditRes by lazy { MutableLiveData<Result<CastResponse>>() }
    private val _recommendationMovieRes by lazy { MutableLiveData<Result<DataList<MovieResponse>>>() }
    private val _trailerRes by lazy { MutableLiveData<Result<VideoResponse>>() }
    private val _reviewsRes by lazy { MutableLiveData<Result<DataList<ReviewsResponse>>>() }

    val detailMovieRes: LiveData<Result<DetailMovieResponse>> get() = _detailMovieRes
    val creditMovieRes: LiveData<Result<CastResponse>> get() = _creditRes
    val recommendationMovieRes: LiveData<Result<DataList<MovieResponse>>> get() = _recommendationMovieRes
    val trailerMovieRes: LiveData<Result<VideoResponse>> get() = _trailerRes
    val reviewsMovieRes: LiveData<Result<DataList<ReviewsResponse>>> get() = _reviewsRes

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