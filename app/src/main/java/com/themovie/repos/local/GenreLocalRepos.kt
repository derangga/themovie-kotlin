package com.themovie.repos.local

import androidx.lifecycle.LiveData
import com.themovie.localdb.dao.GenresDao
import com.themovie.model.local.GenreLocal
import javax.inject.Inject

class GenreLocalRepos
@Inject constructor(private val genresDao: GenresDao){

    suspend fun insert(genre: GenreLocal){
        genresDao.insertGenre(genre)
    }

    suspend fun update(genre: GenreLocal){
        genresDao.updateGenre(genre)
    }

    fun getPartOfGenre(): LiveData<List<GenreLocal>> = genresDao.getPartOfGenre()

    fun getAllGenre(): LiveData<List<GenreLocal>> = genresDao.getAllGenre()
}