package azmithabet.com.news.representation.news.activity.screens.local_news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.domain.usecase.DeleteSavedArticleUseCase
import azmithabet.com.news.domain.usecase.GetSavedArticlesUseCase
import azmithabet.com.news.representation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalNewsViewModel @Inject constructor(
    private val getSavedArticlesUseCase: GetSavedArticlesUseCase,
    private val deleteSavedArticleUseCase: DeleteSavedArticleUseCase
) : BaseViewModel() {
    private var newsJob: Job? = null

    private val _newsState = mutableStateListOf<ArticleItem>()
    var newsState: SnapshotStateList<ArticleItem> = _newsState

    fun getLocalNews() {
        newsJob?.cancel()
        newsJob = getSavedArticlesUseCase().onEach {
            newsState.clear()
            newsState.addAll(it)
        }.launchIn(viewModelScope)
    }
    fun deleteArticle(articleItem: ArticleItem,onSaved:(String)->Unit) {
        newsJob?.cancel()
        newsJob =
            viewModelScope.launch {
                val id = deleteSavedArticleUseCase.invoke(articleItem)
                if (id > 0){
                    onSaved("Article Deleted")
                    getLocalNews()
                }
                else
                    onSaved("Article Not Deleted, Try Again")


            }
    }
    override fun resetState() {
        newsJob?.cancel()
        _newsState.clear()
    }
}