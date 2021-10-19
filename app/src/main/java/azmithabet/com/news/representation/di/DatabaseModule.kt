package azmithabet.com.news.representation.di

import android.app.Application
import androidx.room.Room
import azmithabet.com.news.data.db.ArticleDao
import azmithabet.com.news.data.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app:Application): Database {
        return Room.databaseBuilder(app, Database::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticle(database: Database): ArticleDao {
        return database.getArticleDao()
    }


}