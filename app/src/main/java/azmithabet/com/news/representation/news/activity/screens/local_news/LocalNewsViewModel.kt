package azmithabet.com.news.representation.news.activity.screens.local_news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.domain.usecase.GetSavedNewsUseCase
import azmithabet.com.news.representation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocalNewsViewModel @Inject constructor(
    private val getSavedNewsUseCase: GetSavedNewsUseCase
) : BaseViewModel() {
    private var newsJob: Job? = null

    private val _newsState = mutableStateListOf<ArticleItem>()
    var newsState: SnapshotStateList<ArticleItem> = _newsState

    fun getLocalNews() {
        newsJob?.cancel()
        newsJob = getSavedNewsUseCase().onEach {
            newsState.addAll(it)
        }.launchIn(viewModelScope)
    }

    override fun resetState() {
        newsJob?.cancel()
        _newsState.clear()
    }
}