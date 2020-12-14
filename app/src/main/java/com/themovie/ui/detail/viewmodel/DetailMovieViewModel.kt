package com.themovie.ui.detail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.aldebaran.core.BaseViewModel
import com.aldebaran.network.Result
import com.aldebaran.domain.entities.ui.*
import com.aldebaran.domain.repository.remote.MovieRemoteSource
import com.aldebaran.event.Event

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailMovieViewModel @ViewModelInject constructor(
    private val remote: MovieRemoteSource
) : BaseViewModel() {

    private val _detailMovieRes by lazy { MutableLiveData<DetailMovie>() }
    private val _creditRes by lazy { MutableLiveData<List<Credit>>() }
    private val _recommendationMovieRes by lazy { MutableLiveData<List<Movie>>() }
    private val _trailerRes by lazy { MutableLiveData<List<Video>>() }
    private val _reviewsRes by lazy { MutableLiveData<List<Review>>() }

    private val _loading by lazy { MutableLiveData(true) }
    val loading: LiveData<Boolean> get() = _loading
    private val _eventError by lazy { MutableLiveData<Event<Boolean>>() }
    val eventError: LiveData<Event<Boolean>> get() = _eventError

    val detailMovieRes: LiveData<DetailMovie> get() = _detailMovieRes
    val creditMovieRes: LiveData<List<Credit>> get() = _creditRes
    val recommendationMovieRes: LiveData<List<Movie>> get() = _recommendationMovieRes
    val trailerMovieRes: LiveData<List<Video>> get() = _trailerRes
    val reviewsMovieRes: LiveData<List<Review>> get() = _reviewsRes

    fun getDetailMovieRequest(filmId: Int){
        viewModelScope.launch {
            _loading.value = true
            val detailRes = async(IO) { remote.getDetailMovie(filmId) }
            val creditsRes = async(IO) { remote.getCreditMovie(filmId) }
            val recomRes = async(IO) { remote.getRecommendationMovie(filmId) }
            val trailer = async(IO) { remote.getTrailerMovie(filmId) }
            val reviews = async(IO) { remote.getReviewMovie(filmId) }

            handleDetailMovieResult(detailRes.await())
            handleCreditResult(creditsRes.await())
            handleRecommendedMovieResult(recomRes.await())
            handleTrailerMovieResult(trailer.await())
            handleReviewResult(reviews.await())

        }
    }

    private fun handleDetailMovieResult(detailMovie: Result<DetailMovie>) {
        when (detailMovie) {
            is Result.Success -> {
                _detailMovieRes.value = detailMovie.data
                _loading.value = false
            }
            is Result.Error -> {
                _loading.value = false
                _eventError.value = Event(true)
            }
        }
    }

    private fun handleCreditResult(credits: Result<List<Credit>>) {
        when (credits) {
            is Result.Success -> _creditRes.value = credits.data
            is Result.Error -> Unit
        }
    }

    private fun handleRecommendedMovieResult(recommendedMovie: Result<List<Movie>>) {
        when (recommendedMovie) {
            is Result.Success -> _recommendationMovieRes.value = recommendedMovie.data
            is Result.Error -> Unit
        }
    }

    private fun handleTrailerMovieResult(trailer: Result<List<Video>>) {
        when (trailer) {
            is Result.Success -> _trailerRes.value = trailer.data
            is Result.Error -> Unit
        }
    }

    private fun handleReviewResult(review: Result<List<Review>>) {
        when (review) {
            is Result.Success -> _reviewsRes.value = review.data
            is Result.Error -> Unit
        }
    }
}