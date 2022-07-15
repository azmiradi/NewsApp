package azmithabet.com.news.data.db

import androidx.room.*
import azmithabet.com.news.data.model.artical.ArticleItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleItem: ArticleItem):Long

    @Query("Select * from articles")
    fun getAllArticles():Flow<List<ArticleItem>>

    @Delete
    suspend fun deleteArticle(articleItem: ArticleItem):Int
}