package azmithabet.com.news.representation.di

import azmithabet.com.news.data.db.ArticleDao
import azmithabet.com.news.data.repository.datasource.NewsDatabaseDataSource
import azmithabet.com.news.data.repository.datasourceimpl.NewsDatabaseDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseDataModule {

    @Singleton
    @Provides
    fun provideNewsDatabaseDataSource(articleDao: ArticleDao)
    : NewsDatabaseDataSource {
        return NewsDatabaseDataSourceImpl(articleDao)
    }
}