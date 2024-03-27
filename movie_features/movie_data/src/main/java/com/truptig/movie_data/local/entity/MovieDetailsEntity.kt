package com.truptig.movie_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.truptig.movie_domain.model.MovieDetails


@Entity
data class MovieDetailsEntity(
    val Actors: String,
    val Awards: String,
    val BoxOffice: String,
    val Country: String,
    val DVD: String,
    val Director: String,
    val Genre: String,
    val Language: String,
    val Metascore: String,
    val Plot: String,
    val Poster: String,
    val Production: String,
    val Rated: String,
    val Released: String,
    val Response: String,
    val Runtime: String,
    val Title: String,
    val Type: String,
    val Website: String,
    val Writer: String,
    val Year: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String,
    @PrimaryKey val id: Int? = null
) {
    fun toMovieDetails(): MovieDetails {
        return MovieDetails(
            Actors = Actors,
            Awards = Awards,
            BoxOffice = BoxOffice,
            Country = Country,
            DVD = DVD,
            Director = Director,
            Genre = Genre,
            Language = Language,
            Metascore = Metascore,
            Plot = Plot,
            Poster = Poster,
            Production = Production,
            Rated = Rated,
            Released = Released,
            Response = Response,
            Runtime = Runtime,
            Title = Title,
            Type = Type,
            Website = Website,
            Writer = Writer,
            Year = Year,
            imdbID = imdbID,
            imdbRating = imdbRating,
            imdbVotes = imdbVotes
        )
    }
}