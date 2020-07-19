package com.themovie.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.themovie.helper.CoroutinesTestRule
import com.themovie.repos.RemoteSource
import com.themovie.ui.detail.viewmodel.DetailMovieViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class DetailMovieViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()


    private val remoteSource = mock(RemoteSource::class.java)
    private lateinit var viewModel: DetailMovieViewModel

    @Before
    fun setUp() {
        viewModel =
            DetailMovieViewModel(remoteSource)
    }

    @Test
    fun `get detail movie content`() = coroutinesTestRule
        .testDispatcher.runBlockingTest {
        viewModel.getDetailMovieRequest(1)

        verify(remoteSource).getDetailMovie(1)
        verify(remoteSource).getCreditMovie(1)
        verify(remoteSource).getRecommendationMovie(1)
        verify(remoteSource).getTrailerMovie(1)
        verify(remoteSource).getReviewMovie(1)
    }
}