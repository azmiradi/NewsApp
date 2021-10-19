package azmithabet.com.news.representation.di

import azmithabet.com.news.data.repository.NewsRepositoryImpl
import azmithabet.com.news.data.repository.datasource.NewsDatabaseDataSource
import azmithabet.com.news.data.repository.datasource.NewsRemoteDataSource
import azmithabet.com.news.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsDatabaseDataSource: NewsDatabaseDataSource
    ) : NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsDatabaseDataSource)
    }
}