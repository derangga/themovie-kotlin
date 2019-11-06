package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.GenresDao
import com.themovie.model.db.Genre
import javax.inject.Inject

class GenreLocalRepos
@Inject constructor(private val genresDao: GenresDao){

    suspend fun insert(genre: Genre){
        genresDao.insertGenre(genre)
    }

    suspend fun insert(genre: List<Genre>){
        genresDao.insertGenre(*genre.toTypedArray())
    }

    suspend fun update(genre: Genre){
        genresDao.updateGenre(genre)
    }

    suspend fun update(genre: List<Genre>){
        genresDao.updateGenre(*genre.toTypedArray())
    }

    fun getPartOfGenre(): LiveData<List<Genre>> = genresDao.getPartOfGenre()

    fun getAllGenre(): LiveData<List<Genre>> = genresDao.getAllGenre()
}