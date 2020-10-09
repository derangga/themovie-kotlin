package com.aldebaran.data.repository

import androidx.lifecycle.LiveData
import com.aldebaran.data.resultLiveData
import com.aldebaran.domain.Result
import com.aldebaran.domain.entities.Tv
import com.aldebaran.domain.entities.local.TvEntity
import com.aldebaran.domain.entities.toTvEntity
import com.aldebaran.domain.repository.Repository
import com.aldebaran.domain.repository.local.TvLocalSource
import com.aldebaran.domain.repository.remote.TvRemoteSource

class TvRepository (
    private val local: TvLocalSource,
    private val remote: TvRemoteSource
): Repository.TvRepos {

    override fun getTvFromLocalOrRemote(): LiveData<Result<List<TvEntity>>> {
        return resultLiveData(
            databaseQuery = { local.getDiscoverTv() },
            networkCall = { remote.getDiscoverTv(1) },
            saveCallResult = { res ->
                val rows = local.tvRows()
                if(rows == 0) {
                    res.results.forEach { local.insertDiscoverTv(it.toTvEntity()) }
                } else {
                    res.results.forEachIndexed { key, tv ->
                        local.updateDiscoverTv(tv.toTvEntity(key + 1))
                    }
                }
            }
        )
    }
}