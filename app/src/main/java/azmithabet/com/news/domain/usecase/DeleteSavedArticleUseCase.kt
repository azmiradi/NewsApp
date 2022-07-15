package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteSavedArticleUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(articleItem: ArticleItem) = repository.deleteSavedArticle(articleItem)
}