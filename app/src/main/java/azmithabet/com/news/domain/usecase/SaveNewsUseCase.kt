package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.domain.repository.NewsRepository

class SaveNewsUseCase(private val repository: NewsRepository) {

    suspend fun execute(articlesItem: ArticlesItem):Long{
        return repository.saveNews(articlesItem)
    }
}