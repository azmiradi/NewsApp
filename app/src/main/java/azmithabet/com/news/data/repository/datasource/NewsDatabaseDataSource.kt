package azmithabet.com.news.data.repository.datasource

import azmithabet.com.news.data.model.artical.ArticlesItem
import kotlinx.coroutines.flow.Flow

interface NewsDatabaseDataSource {
    suspend fun insertArticle(articlesItem: ArticlesItem):Long

    suspend fun deleteArticle(articlesItem: ArticlesItem):Int

    fun getAllArticles():Flow<List<ArticlesItem>>
}