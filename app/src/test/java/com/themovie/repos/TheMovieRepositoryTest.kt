package com.themovie.repos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.halobro.getOrAwaitValue
import com.themovie.helper.CoroutinesTestRule
import com.themovie.localdb.LocalSource
import com.themovie.restapi.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TheMovieRepositoryTest {

    private lateinit var localSource: LocalSource
    private lateinit var remoteSource: RemoteSource
    private lateinit var repos: TheMovieRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setUp() {
        localSource = FakeLocalSource()
        remoteSource = FakeRemoteSource()
        repos = TheMovieRepository(localSource, remoteSource)
    }

    @Test
    fun `observe trending`() = coroutinesTestRule
        .testDispatcher.runBlockingTest {
            val result = repos.observeTrending().getOrAwaitValue()

            assertEquals(result.data?.size, 0)
            assertEquals(result.status, Result.Status.SUCCESS)
        }

    @Test
    fun `observe upcoming`() = coroutinesTestRule
        .testDispatcher.runBlockingTest {
            val result = repos.observeUpcoming().getOrAwaitValue()

            assertEquals(result.data?.size, 0)
            assertEquals(result.status, Result.Status.SUCCESS)
        }
}