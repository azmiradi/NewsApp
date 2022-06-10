package azmithabet.com.news.representation.news.activity.screens.news_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.domain.usecase.DeleteSavedNewsUseCase
import azmithabet.com.news.domain.usecase.GetSavedNewsUseCase
import azmithabet.com.news.domain.usecase.SaveNewsUseCase
import azmithabet.com.news.representation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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