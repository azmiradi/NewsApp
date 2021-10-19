package azmithabet.com.news.data.db

import androidx.room.*
import azmithabet.com.news.data.model.artical.ArticlesItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articlesItem: ArticlesItem):Long

    @Query("Select * from articles")
    fun getAllArticles():Flow<List<ArticlesItem>>

    @Delete
    suspend fun deleteArticle(articlesItem: ArticlesItem):Int
}