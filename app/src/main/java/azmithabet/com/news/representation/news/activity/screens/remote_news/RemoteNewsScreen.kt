package azmithabet.com.news.representation.news.activity.screens.remote_news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.data.util.Constants.EGYPT
import azmithabet.com.news.data.util.NewsCategories
import azmithabet.com.news.data.util.showToast
import azmithabet.com.news.representation.news.activity.screens.AlertDialog
import azmithabet.com.news.representation.news.activity.screens.ProgressBar
import azmithabet.com.news.representation.theme.Typography
import bumblebee.io.mid.ui.theme.TeaGreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RemoteNewsScreen(
    onNewsClick: (ArticleItem) -> Unit,
    viewModel: RemoteNewsViewModel = hiltViewModel(),
) {

    val context= LocalContext.current
    val country by rememberSaveable {
        mutableStateOf(EGYPT)
    }
    LaunchedEffect(key1 = country) {
        viewModel.getNewsForAllCategories(country)
    }
    val newsState = viewModel.allNewsState.value
    val pagerState = rememberPagerState()

    val generalNews = newsState.data?.get(NewsCategories.General.category)
    val sportsNews = newsState.data?.get(NewsCategories.Sports.category)
    val entertainmentNews = newsState.data?.get(NewsCategories.Entertainment.category)
    val healthNews = newsState.data?.get(NewsCategories.Health.category)
    val businessNews = newsState.data?.get(NewsCategories.Business.category)
    Box(Modifier.fillMaxSize()
        , contentAlignment = Alignment.Center) {
        LazyColumn(
            Modifier
                .fillMaxSize()
        ) {
            item {
                generalNews?.let {
                    NewsSlider(
                        articles = it, pagerState = pagerState
                        , onNewsClick = onNewsClick)
                }
            }
            item {
                sportsNews?.let {
                    HeaderTitle(string = NewsCategories.Sports.category.uppercase())

                    LazyRow(
                        horizontalArrangement
                        = Arrangement.spacedBy(10.dp),
                    ) {
                        items(
                            items = it
                        ) { articleItem ->

                            NewsItemShape1(
                                modifier = Modifier
                                    .width(140.dp)
                                    .height(230.dp), articleItem = articleItem,
                                onNewsClick=onNewsClick
                            )
                        }
                    }
                }
            }

            businessNews?.let {
                item {
                    HeaderTitle(string = NewsCategories.Business.category.uppercase())
                    LazyRow(
                        horizontalArrangement
                        = Arrangement.spacedBy(10.dp),
                    ) {
                        item {
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                        items(
                            items = it
                        ) { articleItem ->
                            NewsItemShape2(articleItem, onNewsClick = onNewsClick)
                        }

                        item {
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
            }

            entertainmentNews?.let {
                item {
                    HeaderTitle(string = NewsCategories.Entertainment.category.uppercase())
                }
                items(
                    it.windowed(
                        2, 2,
                        true
                    )
                ) { sublist ->
                    Row(Modifier.fillMaxWidth()) {
                        sublist.forEach { articleItem ->
                            NewsItemShape1(
                                modifier = Modifier
                                    .height(230.dp)
                                    .padding(4.dp)
                                    .fillParentMaxWidth(.5f),
                                articleItem,onNewsClick=onNewsClick)
                        }
                    }
                }

            }

        }

        val isLoading= remember() {
            mutableStateOf(false)
        }
        val showAlertDialog= remember{
            mutableStateOf(false)
        }

        isLoading.value=newsState.isLoading
        ProgressBar(isShow = isLoading)
        if (newsState.error.isNotBlank()){
            LaunchedEffect(key1 = newsState.error){
                showAlertDialog.value=true
                context.showToast("Something Error, Sorry")
            }
        }else{
            showAlertDialog.value=false
        }
        if (showAlertDialog.value){
            AlertDialog(
                messageText =  "Something Error, Sorry") {
                viewModel.getNewsForAllCategories(country)
            }
        }

    }

}

@Composable
fun HeaderTitle(string: String) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = TeaGreen
        ) {
            Text(
                text = string, style = Typography.h1,
                modifier = Modifier.padding(start = 20.dp, top = 5.dp, end = 5.dp),
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.height(15.dp))

    }


}
