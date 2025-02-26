package com.themovie.ui.detail

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.themovie.helper.CoroutinesTestRule
//import com.themovie.repos.RemoteSource
//import com.themovie.ui.detail.viewmodel.DetailTvViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runBlockingTest
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.verify
//
//@ExperimentalCoroutinesApi
//class DetailTvViewModelTest{
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var coroutinesTestRule = CoroutinesTestRule()
//
//    private val remoteSource = mock(RemoteSource::class.java)
//    private lateinit var viewModel: DetailTvViewModel
//
//    @Before
//    fun setUp() {
//        viewModel = DetailTvViewModel(remoteSource)
//    }
//
//    @Test
//    fun `get detail tv content`() = coroutinesTestRule
//        .testDispatcher.runBlockingTest {
//            viewModel.getDetailTvRequest(1)
//
//            verify(remoteSource).getDetailTv(1)
//            verify(remoteSource).getCreditTv(1)
//            verify(remoteSource).getRecommendationTv(1)
//            verify(remoteSource).getTrailerTv(1)
//            verify(remoteSource).getReviewTv(1)
//        }
//}