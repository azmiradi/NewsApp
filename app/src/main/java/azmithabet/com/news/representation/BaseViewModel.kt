package azmithabet.com.news.representation

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    abstract fun resetState()
}
