package com.aldebaran.domain.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_genre")
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    val pkId: Int?,
    @ColumnInfo(name = "genreId") val id: Int?,
    @ColumnInfo(name = "name") val name: String?
)