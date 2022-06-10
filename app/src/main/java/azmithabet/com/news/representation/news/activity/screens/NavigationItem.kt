package azmithabet.com.news.representation.news.activity.screens

import com.azmithabet.restaurantsguide.R

sealed class NavigationItem(var route: String, var icon: Int) {
    object RemoteNews : NavigationItem("remote_news", R.drawable.news_icon)
    object LocalNews : NavigationItem("local_news", R.drawable.saved_icon)
}