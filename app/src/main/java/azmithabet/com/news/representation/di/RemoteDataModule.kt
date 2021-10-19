package azmithabet.com.news.representation.di

import azmithabet.com.news.data.api.ApiServices
import azmithabet.com.news.data.repository.datasource.NewsRemoteDataSource
import azmithabet.com.news.data.repository.datasourceimpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(apiServices: ApiServices)
    : NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(apiServices)
    }
}