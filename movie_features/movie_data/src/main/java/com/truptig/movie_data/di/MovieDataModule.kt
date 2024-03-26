package com.truptig.movie_data.di

import android.app.Application
import androidx.room.Room
import com.truptig.movie_data.local.MovieDatabase
import com.truptig.movie_data.remote.MovieApi
import com.truptig.movie_data.repository.MovieRepositoryImpl
import com.truptig.movie_domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieDataModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app, MovieDatabase::class.java, "movie_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        db: MovieDatabase,
        api: MovieApi
    ): MovieRepository {
        return MovieRepositoryImpl(api, db.dao)
    }

}