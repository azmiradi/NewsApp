package azmithabet.com.news.data.repository.datasourceimpl

import azmithabet.com.news.data.db.ArticleDao
import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.data.repository.datasource.NewsDatabaseDataSource
import kotlinx.coroutines.flow.Flow

class NewsDatabaseDataSourceImpl(
    private val articleDao: ArticleDao
) : NewsDatabaseDataSource {

    override suspend fun insertArticle(articlesItem: ArticlesItem):Long {
       return   articleDao.insertArticle(articlesItem)
    }

    override fun getAllArticles(): Flow<List<ArticlesItem>> {
        return articleDao.getAllArticles()
    }
    override suspend fun deleteArticle(articlesItem: ArticlesItem):Int {
        return   articleDao.deleteArticle(articlesItem)
    }

}