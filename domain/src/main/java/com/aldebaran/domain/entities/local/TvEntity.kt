package com.aldebaran.domain.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aldebaran.domain.entities.Tv

@Entity(tableName = "tbl_tv")
data class TvEntity(
    @PrimaryKey(autoGenerate = true)
    val pkId: Int = 0,
    @ColumnInfo(name = "tvId") override val id: Int?,
    @ColumnInfo(name = "title") override val name: String?,
    @ColumnInfo(name = "vote_average") override val voteAverage: String?,
    @ColumnInfo(name = "vote_count") override val voteCount: String?,
    @ColumnInfo(name = "poster_path") override val posterPath: String?,
    @ColumnInfo(name = "backdrop_path") override val backdropPath: String?,
    @ColumnInfo(name = "overview") override val overview: String?
): Tv