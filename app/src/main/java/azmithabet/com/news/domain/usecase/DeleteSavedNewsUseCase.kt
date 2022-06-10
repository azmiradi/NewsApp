package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val repository: NewsRepository) {
    suspend fun execute(articleItem: ArticleItem)=repository.deleteSavedNews(articleItem)
}