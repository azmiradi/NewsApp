package azmithabet.com.news.representation.di

import azmithabet.com.news.representation.news.adapter.CategoryAdapter
import azmithabet.com.news.representation.news.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdaptorsModule {

    @Singleton
    @Provides
    fun provideNewsAdaptor(): NewsAdapter {
        return NewsAdapter()
    }

    @Singleton
    @Provides
    fun provideCategoryAdaptor(): CategoryAdapter {
        return CategoryAdapter()
    }
}