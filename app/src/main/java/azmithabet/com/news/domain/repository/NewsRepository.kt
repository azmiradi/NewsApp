package azmithabet.com.news.domain.repository

import azmithabet.com.news.data.model.artical.ApiResponse
import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(country: String, page: Int,category: String): Resource<ApiResponse>

    suspend fun getSearchNews(querySearch: String,country: String, page: Int,category: String): Resource<ApiResponse>

    fun getSavedNews(): Flow<List<ArticlesItem>>

    suspend fun deleteSavedNews(articlesItem: ArticlesItem):Int

    suspend fun saveNews(articlesItem: ArticlesItem):Long

}