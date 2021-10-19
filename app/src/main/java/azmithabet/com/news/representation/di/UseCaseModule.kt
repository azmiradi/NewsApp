package azmithabet.com.news.representation.di

import azmithabet.com.news.domain.repository.NewsRepository
import azmithabet.com.news.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideDeleteSavedNewsUseCase(repository: NewsRepository): DeleteSavedNewsUseCase{
        return DeleteSavedNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveNewsUseCase(repository: NewsRepository): SaveNewsUseCase{
        return SaveNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSearchNewsUseCase(repository: NewsRepository): SearchNewsUseCase{
        return SearchNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCase(repository: NewsRepository): GetSavedNewsUseCase{
        return GetSavedNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetNewsUseCase(repository: NewsRepository): GetNewsUseCase{
        return GetNewsUseCase(repository)
    }
}