package com.truptig.movie_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.truptig.movie_data.local.entity.MovieDetailsEntity
import com.truptig.movie_data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailsEntity::class],
    version = 1
)

abstract class MovieDatabase: RoomDatabase() {

    abstract val dao: MovieDao

}