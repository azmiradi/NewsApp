package azmithabet.com.news.data.repository.datasource

import azmithabet.com.news.data.model.artical.ArticleItem
import kotlinx.coroutines.flow.Flow

interface NewsDatabaseDataSource {
    suspend fun insertArticle(articleItem: ArticleItem):Long

    suspend fun deleteArticle(articleItem: ArticleItem):Int

    fun getAllArticles():Flow<List<ArticleItem>>
}