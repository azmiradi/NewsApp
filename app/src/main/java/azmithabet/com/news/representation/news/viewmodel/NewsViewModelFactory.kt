package azmithabet.com.news.representation.news.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import azmithabet.com.news.domain.usecase.*

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsUseCase: GetNewsUseCase,
    private val getSearchNewsUseCase: SearchNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return NewsViewModel(
            app, getNewsUseCase, getSearchNewsUseCase,
            saveNewsUseCase, getSavedNewsUseCase, deleteSavedNewsUseCase
        ) as T
    }
}