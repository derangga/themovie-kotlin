package com.themovie.local

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.core.app.ApplicationProvider.getApplicationContext
//import androidx.room.Room
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.SmallTest
//import com.themovie.localdb.TheMovieDatabase
//import com.themovie.model.db.Trending
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.take
//import kotlinx.coroutines.flow.toList
//import kotlinx.coroutines.test.runBlockingTest
//import org.hamcrest.CoreMatchers.`is`
//import org.junit.After
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//@SmallTest
//class TrendingDaoTest {
//
//    private lateinit var database: TheMovieDatabase
//
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    @Before
//    fun setUp() {
//        database = Room.inMemoryDatabaseBuilder(
//            getApplicationContext(),
//            TheMovieDatabase::class.java
//        ).build()
//    }
//
//    @After
//    fun tearDown() {
//        database.close()
//    }
//
//    @Test
//    fun insertTrendingMovieAndGetTrendingMovie() =
//         runBlockingTest {
//             // given
//             val movie = Trending(1, 1, "Pokemon")
//             database.trendingDao().insert(movie)
//
//             // when
//             val loaded = database.trendingDao().getTrending().take(1).toList().first()
//
//             // then
//             assertThat(loaded[0].id, `is`(1))
//             assertThat(loaded[0].title, `is`("Pokemon"))
//        }
//
//    @Test
//    fun insertTrendingMovieAndGetCountRow() = runBlockingTest {
//        // given
//        val movie = listOf(
//            Trending(1, 1, "Pokemon"),
//            Trending(2, 2, "Ultraman")
//        )
//        database.trendingDao().insertAll(*movie.toTypedArray())
//
//        // when
//        val row = database.trendingDao().countRows()
//
//        // then
//        assertThat(row, `is`(2))
//    }
//}