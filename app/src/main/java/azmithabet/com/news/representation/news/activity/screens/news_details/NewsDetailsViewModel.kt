package azmithabet.com.news.representation.news.activity.screens.news_details

import androidx.lifecycle.viewModelScope
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.domain.usecase.SaveNewsUseCase
import azmithabet.com.news.representation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailsViewModel @Inject constructor(
    private val savedNewsUseCase: SaveNewsUseCase
) : BaseViewModel() {
    private var newsJob: Job? = null
    fun saveArticle(articleItem: ArticleItem, onSaved: (String) -> Unit) {
        newsJob?.cancel()
        newsJob =
            viewModelScope.launch {
                val id = savedNewsUseCase.invoke(articleItem)
                onSaved(
                    if (id > 0)
                        "Article Saved"
                    else
                        "Article Not Saved, Try Again"
                )

            }
    }

    override fun resetState() {
        newsJob?.cancel()
    }
}