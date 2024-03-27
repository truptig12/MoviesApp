package com.truptig.movie_domain.di


import com.truptig.movie_domain.repository.MovieRepository
import com.truptig.movie_domain.use_case.GetMovieData
import com.truptig.movie_domain.use_case.GetMovieDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class MovieDomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetWordInfoUseCase(repository: MovieRepository): GetMovieData {
        return GetMovieData(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetMovieDetailsUseCase(repository: MovieRepository): GetMovieDetails {
        return GetMovieDetails(repository)
    }
}