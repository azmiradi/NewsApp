package azmithabet.com.news.representation.news.activity.screens

import azmithabet.com.news.representation.news.activity.screens.NavigationDestination.LOCAL_NEWS_ITEM
import azmithabet.com.news.representation.news.activity.screens.NavigationDestination.REMOTE_NEWS_ITEM
import com.azmithabet.restaurantsguide.R

sealed class NavigationItem(var route: String, var icon: Int) {
    object RemoteNews : NavigationItem(REMOTE_NEWS_ITEM, R.drawable.news_icon)
    object LocalNews : NavigationItem(LOCAL_NEWS_ITEM, R.drawable.saved_icon)
}
object NavigationDestination{
    const val REMOTE_NEWS_ITEM="remote_news"
    const val LOCAL_NEWS_ITEM="local_news"
    const val NEWS_DETAILS="news_details/{article_item}"
}