package azmithabet.com.news.domain.repository

import azmithabet.com.news.data.model.artical.ApiResponse
import azmithabet.com.news.data.model.artical.ArticleItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
    suspend fun getNews(country: String, page: Int,category: String,query:String?=null): Response<ApiResponse>


    fun getSavedArticles(): Flow<List<ArticleItem>>

    suspend fun deleteSavedArticle(articleItem: ArticleItem):Int

    suspend fun saveArticle(articleItem: ArticleItem):Long

}