package azmithabet.com.news.representation.news.activity.screens.remote_news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.data.util.DataState
import azmithabet.com.news.data.util.NewsCategories
import azmithabet.com.news.data.util.resetState
import azmithabet.com.news.domain.usecase.GetNewsUseCase
import azmithabet.com.news.representation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class RemoteNewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : BaseViewModel() {

    private var newsJob: Job? = null
    private val _allNewsState = mutableStateOf(DataState<Map<String, List<ArticleItem>?>>())
    val allNewsState: State<DataState<Map<String, List<ArticleItem>?>>> = _allNewsState

    fun getNewsForAllCategories(country:String) {
        _allNewsState.value = DataState(true)
        val responseDataMap = HashMap<String, List<ArticleItem>?>()
        val generalNewsFlow = getNewsUseCase(country, 1, NewsCategories.General.category)
        val sportsNewsFlow = getNewsUseCase(country, 1, NewsCategories.Sports.category)
        val healthNewsFlow = getNewsUseCase(country, 1, NewsCategories.Health.category)
        val businessNewsFlow = getNewsUseCase(country, 1, NewsCategories.Business.category)
        val entertainmentNewsFlow = getNewsUseCase(country, 1, NewsCategories.Entertainment.category)

        newsJob?.cancel()
        newsJob = combine(
            generalNewsFlow, sportsNewsFlow, healthNewsFlow,
            businessNewsFlow, entertainmentNewsFlow
        )
        { general, sports, health, business, entertainment ->
            responseDataMap[NewsCategories.General.category] = general?.filterNotNull()
            responseDataMap[NewsCategories.Sports.category] = sports?.filterNotNull()
            responseDataMap[NewsCategories.Health.category] = health?.filterNotNull()
            responseDataMap[NewsCategories.Business.category] = business?.filterNotNull()
            responseDataMap[NewsCategories.Entertainment.category] = entertainment?.filterNotNull()
            _allNewsState.value = DataState(data = responseDataMap)

        }.catch { error ->
            _allNewsState.value = DataState(error = error.message.toString())
        }.launchIn(viewModelScope)
    }

    override fun resetState() {
        newsJob?.cancel()
        _allNewsState.resetState()
    }
}

