package com.themovie.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.themovie.localdb.TheMovieDatabase
import com.themovie.model.db.Trending
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class MoviesDaoTest {

    private lateinit var database: TheMovieDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TheMovieDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTrendingMovieAndGetAll() {
        runBlockingTest {
            val movie = Trending(1, 1, "Pokemon")
            database.trendingDao().insert(movie)

            val loaded = database.trendingDao().getTrending()

            loaded.collect { db ->
                val res = db.first()
                assertThat(res.pkId, `is`(1))
                assertThat(res.id, `is`(1))
                assertThat(res.title, `is`("Pokemon"))
            }
        }
    }
}