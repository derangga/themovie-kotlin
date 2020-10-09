package com.aldebaran.domain.entities.remote

import com.aldebaran.domain.entities.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse (
    @SerializedName("genres") val genres: List<GenreRemote>
)

data class GenreRemote(
    @SerializedName("id") override val id: Int?,
    @SerializedName("name") override val name: String?
) : Genre