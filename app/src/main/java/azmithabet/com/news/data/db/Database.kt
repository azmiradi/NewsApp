package azmithabet.com.news.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import azmithabet.com.news.data.model.artical.ArticleItem

@Database(entities = [ArticleItem::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract  class Database : RoomDatabase() {

    abstract fun getArticleDao():ArticleDao

}