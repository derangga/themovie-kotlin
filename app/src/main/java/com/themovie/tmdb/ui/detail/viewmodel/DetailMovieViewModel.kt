package com.themovie.tmdb.ui.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themovie.core.event.Event
import com.themovie.core.network.Result
import com.themovie.datasource.entities.ui.Credit
import com.themovie.datasource.entities.ui.DetailMovie
import com.themovie.datasource.entities.ui.Movie
import com.themovie.datasource.entities.ui.Review
import com.themovie.datasource.entities.ui.Video
import com.themovie.datasource.repository.remote.MovieRemoteSource
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val remote: MovieRemoteSource
) : ViewModel() {

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

    fun getDetailMovieRequest(filmId: Int) {
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