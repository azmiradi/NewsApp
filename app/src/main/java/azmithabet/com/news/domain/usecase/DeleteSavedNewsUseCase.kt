package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val repository: NewsRepository) {
    suspend fun execute(articlesItem: ArticlesItem)=repository.deleteSavedNews(articlesItem)
}