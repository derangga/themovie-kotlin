package com.aldebaran.domain.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aldebaran.domain.entities.Genre

@Entity(tableName = "tbl_genre")
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    val pkId: Int = 0,
    @ColumnInfo(name = "genreId") override val id: Int?,
    @ColumnInfo(name = "name") override val name: String?
): Genre