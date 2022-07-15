package azmithabet.com.news.data.repository.datasourceimpl

import azmithabet.com.news.data.db.ArticleDao
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.data.repository.datasource.NewsDatabaseDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsDatabaseDataSourceImpl @Inject constructor(
    private val articleDao: ArticleDao
) : NewsDatabaseDataSource {

    override suspend fun insertArticle(articleItem: ArticleItem):Long {
       return   articleDao.insertArticle(articleItem)
    }

    override fun getAllArticles(): Flow<List<ArticleItem>> {
        return articleDao.getAllArticles()
    }
    override suspend fun deleteArticle(articleItem: ArticleItem):Int {
        return   articleDao.deleteArticle(articleItem)
    }

}