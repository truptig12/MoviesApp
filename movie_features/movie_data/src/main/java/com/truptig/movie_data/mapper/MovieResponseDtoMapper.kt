package com.truptig.movie_data.mapper

import com.truptig.movie_data.local.entity.MovieEntity
import com.truptig.movie_data.remote.dto.MovieDto
import com.truptig.movie_domain.model.Movie


fun MovieDto.mapFromEntity() = Movie(
    Poster = this.Poster,
    Title = this.Title,
    Type = this.Type,
    Year = this.Year,
    imdbID = this.imdbID
)

fun Movie.mapFromDomain() = MovieDto(
    Poster = this.Poster,
    Title = this.Title,
    Type = this.Type,
    Year = this.Year,
    imdbID = this.imdbID
)

fun List<MovieEntity>.mapFromListModel(): List<Movie> {
    return this.map {
        it.toMovie()
    }
}

fun List<Movie>.mapFromListDomain(): List<MovieDto> {
    return this.map {
        it.mapFromDomain()
    }
}