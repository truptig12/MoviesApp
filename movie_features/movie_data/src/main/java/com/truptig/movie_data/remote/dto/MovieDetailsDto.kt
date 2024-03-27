package com.truptig.movie_data.remote.dto

import com.google.gson.annotations.SerializedName
import com.truptig.movie_data.local.entity.MovieDetailsEntity
import java.io.Serializable


class MovieDetailsDto(
    @SerializedName("Title")
    val Title: String,
    @SerializedName("Year")
    val Year: String,
    @SerializedName("Rated")
    val Rated: String,
    @SerializedName("Released")
    val Released: String,
    @SerializedName("Runtime")
    val Runtime: String,
    @SerializedName("Genre")
    val Genre: String,
    @SerializedName("Director")
    val Director: String,
    @SerializedName("Writer")
    val Writer: String,
    @SerializedName("Actors")
    val Actors: String,
    @SerializedName("Plot")
    val Plot: String,
    @SerializedName("Language")
    val Language: String,
    @SerializedName("Country")
    val Country: String,
    @SerializedName("Awards")
    val Awards: String,
    @SerializedName("Poster")
    val Poster: String,
    @SerializedName("Metascore")
    val Metascore: String,
    @SerializedName("imdbRating")
    val imdbRating: String,
    @SerializedName("imdbVotes")
    val imdbVotes: String,
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Type")
    val Type: String,
    @SerializedName("Search")
    val DVD: String?,
    @SerializedName("BoxOffice")
    val BoxOffice: String?,
    @SerializedName("Production")
    val Production: String?,
    @SerializedName("Website")
    val Website: String?,
    @SerializedName("Response")
    val Response: String

) : Serializable {
    fun toMovieDetailsEntity(): MovieDetailsEntity {
        return MovieDetailsEntity(
            Actors = Actors,
            Awards = Awards,
            BoxOffice = BoxOffice.toString(),
            Country = Country,
            DVD = DVD.toString(),
            Director = Director,
            Genre = Genre,
            Language = Language,
            Metascore = Metascore,
            Plot = Plot,
            Poster = Poster,
            Production = Production.toString(),
            Rated = Rated,
            Released = Released,
            Response = Response,
            Runtime = Runtime,
            Title = Title,
            Type = Type,
            Website = Website.toString(),
            Writer = Writer,
            Year = Year,
            imdbID = imdbID,
            imdbRating = imdbRating,
            imdbVotes = imdbVotes
        )
    }
}