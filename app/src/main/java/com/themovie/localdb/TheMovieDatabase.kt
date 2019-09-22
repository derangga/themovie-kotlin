package com.themovie.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.themovie.localdb.dao.MoviesDao
import com.themovie.localdb.dao.TrendingDao
import com.themovie.localdb.dao.TvDao
import com.themovie.localdb.dao.UpcomingDao
import com.themovie.model.local.MoviesLocal
import com.themovie.model.local.Trending
import com.themovie.model.local.TvLocal
import com.themovie.model.local.Upcoming

@Database(entities = [Trending::class, Upcoming::class, TvLocal::class, MoviesLocal::class], version = 2, exportSchema = false)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun trendingDao(): TrendingDao
    abstract fun upcomingDao(): UpcomingDao
    abstract fun discoverMovies(): MoviesDao
    abstract fun discoverTv(): TvDao

    companion object {
        @Volatile
        private var INSTANCE: TheMovieDatabase? = null

        fun getDatabase(context: Context): TheMovieDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TheMovieDatabase::class.java,
                    "TheMovieDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}