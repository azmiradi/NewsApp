package azmithabet.com.news.data.repository.datasource

import azmithabet.com.news.data.model.artical.ApiResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getNews(country: String, page: Int,category:String,query: String? = null):Response<ApiResponse>

}