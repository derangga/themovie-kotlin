package com.themovie.ui.person

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.themovie.helper.CoroutinesTestRule
import com.themovie.repos.RemoteSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class PersonViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val remoteSource = mock(RemoteSource::class.java)
    private lateinit var viewModel: PersonViewModel

    @Before
    fun setUp() {
        viewModel = PersonViewModel(remoteSource)
    }

    @Test
    fun `get content person detail`() = coroutinesTestRule
        .testDispatcher.runBlockingTest {
            viewModel.getDetailPersonRequest(10)

            verify(remoteSource).getDetailPerson(10)
            verify(remoteSource).getPersonPict(10)
            verify(remoteSource).getPersonFilm(10)
        }
}