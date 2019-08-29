package com.themovie.repos

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.themovie.dao.UpcomingDao
import com.themovie.localdb.TheMovieDatabase
import com.themovie.model.local.Upcoming
import org.jetbrains.anko.doAsync

class UpcomingLocalRepos(application: Application) {

    private var upcomingDao: UpcomingDao
    private var allUpcomingMovie: LiveData<List<Upcoming>>
    init {
        val themovieDatabase =  TheMovieDatabase.getDatabase(application)
        upcomingDao = themovieDatabase.upcomingDao()
        allUpcomingMovie = upcomingDao.getAllUpcomingMv()
    }

    fun insert(upcoming: Upcoming){
        doAsync {
            upcomingDao.insertUpcoming(upcoming)
        }
    }

    fun update(upcoming: Upcoming){
        doAsync {
            upcomingDao.updateUpcoming(upcoming)
        }
    }

    fun getAllUpcoming(): LiveData<List<Upcoming>>{
        return allUpcomingMovie
    }

    companion object{
        private class InsertAsyncTask(private var upcomingDao: UpcomingDao): AsyncTask<Upcoming, Void, Void>() {
            override fun doInBackground(vararg upcoming: Upcoming?): Void? {
                upcomingDao.insertUpcoming(upcoming[0]!!)
                return null
            }
        }

        private class UpdateAsyncTask(private var upcomingDao: UpcomingDao): AsyncTask<Upcoming, Void, Void>(){
            override fun doInBackground(vararg upcoming: Upcoming?): Void? {
                upcomingDao.updateUpcoming(upcoming[0]!!)
                return null
            }
        }
    }
}