package azmithabet.com.news.domain.usecase

import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.data.util.Constants.SUCCESS_CODE
import azmithabet.com.news.data.util.Resource
import azmithabet.com.news.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    operator fun invoke(country: String, page: Int, category: String, query: String? = null)
            : Flow<List<ArticleItem?>?> = flow {
        try {
            val response = repository.getNews(country, page, category, query)

            if (response.code() == SUCCESS_CODE) {
                response.body()?.let { it ->
                    emit(it.articles)
                }
                return@flow
            } else {
                response.errorBody()?.let {
                    throw Exception(it.string())

                }
                throw Exception(response.message())
            }

        } catch (http: HttpException) {
            throw Exception(http.message())

        } catch (io: IOException) {
            throw Exception(io.message)

        }
    }.flowOn(Dispatchers.IO)
}