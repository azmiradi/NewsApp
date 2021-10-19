package azmithabet.com.news.data.repository

import azmithabet.com.news.data.model.artical.ApiResponse
import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.data.repository.datasource.NewsDatabaseDataSource
import azmithabet.com.news.data.repository.datasource.NewsRemoteDataSource
import azmithabet.com.news.data.util.Resource
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
        category: String
    ): Resource<ApiResponse> {
        return responseToResource(newsRemoteDataSource.getNews(country, page, category))
    }

    private fun responseToResource(response: Response<ApiResponse>): Resource<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getSearchNews(
        querySearch: String,
        country: String,
        page: Int,
        category: String
    ): Resource<ApiResponse> {
        return responseToResource(
            newsRemoteDataSource.getSearchNews(
                querySearch,
                country,
                page,
                category
            )
        )
    }

    override fun getSavedNews(): Flow<List<ArticlesItem>> {
       return newsDatabaseDataSource.getAllArticles()
    }

    override suspend fun deleteSavedNews(articlesItem: ArticlesItem):Int {
         return newsDatabaseDataSource.deleteArticle(articlesItem)
    }

    override suspend fun saveNews(articlesItem: ArticlesItem):Long {
         return newsDatabaseDataSource.insertArticle(articlesItem)
    }


}