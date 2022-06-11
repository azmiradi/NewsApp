package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.domain.repository.NewsRepository
import javax.inject.Inject

class SaveNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    suspend operator fun invoke(articleItem: ArticleItem):Long{
        return repository.saveArticle(articleItem)
    }
}