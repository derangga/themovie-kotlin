package com.themovie.restapi

class ApiUrl {
    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val UPCOMING = "movie/upcoming"
        const val TRENDING = "trending/tv/day"
        const val DISCOVER_MOVIES = "discover/movie"
        const val DISCOVER_TV = "discover/tv"
        const val DETAIL_MOVIE = "movie/{movie_id}"
        const val DETAIL_TV = "tv/{tv_id}"
        const val RECOMMENDATION = "movie/{movie_id}/recommendations"
        const val RECOMMENDATION_TV = "tv/{tv_id}/recommendations"
        const val REVIEWS = "movie/{movie_id}/reviews"
        const val REVIEWS_TV = "tv/{tv_id}/reviews"
        const val CREDITS = "movie/{movie_id}/credits"
        const val TOKEN = "98e5a808562ae58e4db7aecb3a0eda01"
        const val IMG_BACK = "https://image.tmdb.org/t/p/w780"
        const val IMG_POSTER = "https://image.tmdb.org/t/p/w185"
        const val VIDEO_MOVIE = "movie/{movie_id}/videos"
        const val VIDEO_TV = "tv/{tv_id}/videos"
        const val THUMBNAIL = "https://img.youtube.com/vi/key/0.jpg"
        const val PERSON_FILM = "person/{person_id}/movie_credits"
        const val BIOGRAPHY = "person/{person_id}"
    }
}