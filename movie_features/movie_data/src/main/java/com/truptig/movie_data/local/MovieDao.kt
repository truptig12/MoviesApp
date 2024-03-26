package com.truptig.movie_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.truptig.movie_data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movieentity")
    suspend fun deleteMovies()

    @Query("SELECT * FROM movieentity ORDER BY year ASC")
    suspend fun getAllMovies(): List<MovieEntity>
}