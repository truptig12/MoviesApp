package com.truptig.movie_data.remote.dto

import com.truptig.movie_data.local.entity.MovieEntity

data class MovieDto(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String
) {
    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            Poster = Poster,
            Title = Title,
            Type = Type,
            Year = Year,
            imdbID = imdbID
        )
    }
}