package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ApiResponse
import azmithabet.com.news.data.util.Resource
import azmithabet.com.news.domain.repository.NewsRepository

class GetNewsUseCase(private val repository: NewsRepository) {
   suspend fun execute(country: String, page: Int,category:String):Resource<ApiResponse>{
        return repository.getNews(country, page,category)
    }
}