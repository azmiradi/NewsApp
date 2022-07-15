package azmithabet.com.news.representation.news.activity.screens.local_news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.data.util.showToast
import azmithabet.com.news.representation.news.activity.screens.AlertDialog
import azmithabet.com.news.representation.news.activity.screens.remote_news.NewsItemShape1


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocalScreen(
    onNewsClick: (ArticleItem) -> Unit,
    viewModel: LocalNewsViewModel = hiltViewModel()
) {
    Box(
        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(key1 = Unit) {
            viewModel.getLocalNews()
        }
        val newsArticle = viewModel.newsState
        val context = LocalContext.current
        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            items(newsArticle) { article ->
                NewsItemShape1(
                    modifier = Modifier
                        .height(230.dp)
                        .padding(4.dp),
                    article, onNewsClick = onNewsClick,
                    isLocal = true
                ) {
                    viewModel.deleteArticle(it) {
                        context.showToast(it)
                    }
                }
            }
        }
        val showAlertDialog = remember {
            mutableStateOf(false)
        }
        showAlertDialog.value = viewModel.newsState.isEmpty()

        if (showAlertDialog.value) {
            AlertDialog(
                messageText = "Not Found Data"
            ) {
                viewModel.getLocalNews()
            }
        }
    }

}