package com.truptig.movie_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.truptig.movie_domain.model.Movie


@Entity
data class MovieEntity(
    val Poster: String,
    val Title: String,
    val Type: String,
    val Year: String,
    val imdbID: String,
    @PrimaryKey val id: Int? = null
) {
    fun toMovie(): Movie {
        return Movie(
            Poster = Poster,
            Title = Title,
            Type = Type,
            Year = Year,
            imdbID = imdbID
        )
    }
}