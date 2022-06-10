package azmithabet.com.news.representation.news.activity.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.representation.news.activity.screens.local_news.LocalScreen
import azmithabet.com.news.representation.news.activity.screens.news_details.NewsDetails
import azmithabet.com.news.representation.news.activity.screens.remote_news.RemoteNewsScreen
import bumblebee.io.mid.ui.theme.BlueDark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope= rememberCoroutineScope()
    var articleItem by remember {
        mutableStateOf<ArticleItem?>(null)
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        BottomSheetScaffold(
            sheetGesturesEnabled=false,
            sheetContent ={
                articleItem?.let {
                    NewsDetails(it)
                }
            }, scaffoldState = scaffoldState,
        ) {
            NavigationControl(navController, innerPadding){
                articleItem=it
                coroutineScope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }
        }
    }

    BackHandler {
        if (scaffoldState.bottomSheetState.isExpanded)
        {
            coroutineScope.launch {
                scaffoldState.bottomSheetState.collapse()
            }
        } else{
            navController.popBackStack()
        }
    }

}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.RemoteNews,
        NavigationItem.LocalNews,
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = ""
                    )
                },
                selectedContentColor = BlueDark,
                unselectedContentColor = Color.Gray.copy(0.4f),
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationControl(
    navController: NavHostController,
    innerPadding: PaddingValues,
    onNewsClick:(ArticleItem)->Unit
){
    NavHost(
        navController, startDestination = NavigationItem.RemoteNews.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(
            NavigationItem.RemoteNews.route
        ) {
            RemoteNewsScreen(onNewsClick)
        }

        composable(NavigationItem.LocalNews.route) {
            LocalScreen(onNewsClick)
        }


    }
}