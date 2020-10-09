package com.aldebaran.domain.entities

interface Movie {
    val id: Int?
    val title: String?
    val posterPath: String?
    val backdropPath: String?
    val overview: String?
    val voteAverage: String?
    val releaseDate: String?
}