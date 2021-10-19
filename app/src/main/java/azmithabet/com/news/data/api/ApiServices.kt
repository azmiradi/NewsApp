package azmithabet.com.news.data.api

import azmithabet.com.news.BuildConfig
import azmithabet.com.news.data.model.artical.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("page") page: Int,@Query("category") category: String,
        @Query("apiKey") apiKey: String=BuildConfig.NEWS_KEY
    ): Response<ApiResponse>

    @GET("v2/top-headlines")
    suspend fun getSearchNews(
        @Query("q") querySearch: String,
        @Query("country") country: String,
        @Query("page") page: Int,@Query("category") category: String,
        @Query("apiKey") apiKey: String=BuildConfig.NEWS_KEY
    ): Response<ApiResponse>
}