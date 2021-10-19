package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val repository: NewsRepository) {

      fun execute():Flow<List<ArticlesItem>>{
          return repository.getSavedNews()
      }

}