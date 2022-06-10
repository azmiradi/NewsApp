package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedNewsUseCase @Inject constructor(private val repository: NewsRepository) {

      operator fun invoke():Flow<List<ArticleItem>>{
          return repository.getSavedNews()
      }

}