package azmithabet.com.news.data.repository.datasourceimpl

import azmithabet.com.news.data.api.ApiServices
import azmithabet.com.news.data.model.artical.ApiResponse
import azmithabet.com.news.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val apiServices: ApiServices
) : NewsRemoteDataSource {

    override suspend fun getNews(country: String, page: Int,category:String): Response<ApiResponse> {
        return apiServices.getNews(country,page,category)
    }
    override suspend fun getSearchNews(querySearch: String, country: String,  page: Int,category:String): Response<ApiResponse> {
        return apiServices.getSearchNews(querySearch,country,page,category)
    }
}