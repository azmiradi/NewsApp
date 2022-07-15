package azmithabet.com.news.data.api

import azmithabet.com.news.data.model.artical.ApiResponse
import com.azmithabet.restaurantsguide.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("q") querySearch: String?=null,
        @Query("page") page: Int,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String= BuildConfig.NEWS_KEY
    ): Response<ApiResponse>
}