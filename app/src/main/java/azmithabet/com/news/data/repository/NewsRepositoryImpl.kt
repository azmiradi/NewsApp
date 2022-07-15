package azmithabet.com.news.data.repository

import azmithabet.com.news.data.model.artical.ApiResponse
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.data.repository.datasource.NewsDatabaseDataSource
import azmithabet.com.news.data.repository.datasource.NewsRemoteDataSource
import azmithabet.com.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsDatabaseDataSource: NewsDatabaseDataSource
) : NewsRepository {

    override suspend fun getNews(
        country: String,
        page: Int,
        category: String,
        query: String?
    ): Response<ApiResponse> {
        return  newsRemoteDataSource.getNews(country, page, category,query)
    }
    override fun getSavedArticles(): Flow<List<ArticleItem>> {
       return newsDatabaseDataSource.getAllArticles()
    }

    override suspend fun deleteSavedArticle(articleItem: ArticleItem):Int {
         return newsDatabaseDataSource.deleteArticle(articleItem)
    }

    override suspend fun saveArticle(articleItem: ArticleItem):Long {
         return newsDatabaseDataSource.insertArticle(articleItem)
    }


}