package azmithabet.com.news.representation.news.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import azmithabet.com.news.data.model.artical.ApiResponse
import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.data.util.Resource
import azmithabet.com.news.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsUseCase: GetNewsUseCase,
    private val getSearchNewsUseCase: SearchNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : AndroidViewModel(app) {

    val newsStream: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    val searchNewsStream: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun getNews(country: String, page: Int, category: String) =
        viewModelScope.launch(Dispatchers.IO) {
            newsStream.postValue(Resource.Loading())
            if (isNetworkAvailable(app)) {
                try {
                    val apiResult = getNewsUseCase.execute(country, page, category)
                    newsStream.postValue(apiResult)
                } catch (exception: Exception) {
                    newsStream.postValue(Resource.Error(exception.message.toString()))
                }
            } else {
                newsStream.postValue(Resource.Error("No Internet"))
            }

        }


    fun getSearchNews(querySearch: String, country: String, page: Int, category: String) =
        viewModelScope.launch(Dispatchers.IO) {
            searchNewsStream.postValue(Resource.Loading())
            if (isNetworkAvailable(app)) {
                try {
                    val apiResult =
                        getSearchNewsUseCase.execute(querySearch, country, page, category)
                    searchNewsStream.postValue(apiResult)
                } catch (exception: Exception) {
                    searchNewsStream.postValue(Resource.Error(exception.message.toString()))
                }
            } else {
                searchNewsStream.postValue(Resource.Error("No Internet"))
            }

        }


    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

    fun saveArticle(articlesItem: ArticlesItem) = liveData {
        val id = saveNewsUseCase.execute(articlesItem)
        emit(id)
    }

    fun getSavedArticles() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    fun deleteArticle(articlesItem: ArticlesItem) = liveData {
        val id = deleteSavedNewsUseCase.execute(articlesItem)
        emit(id)
    }
}