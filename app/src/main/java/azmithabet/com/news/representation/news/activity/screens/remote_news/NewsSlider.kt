package azmithabet.com.news.representation.news.activity.screens.remote_news

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import azmithabet.com.news.data.model.artical.ArticleItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import bumblebee.io.mid.ui.theme.YellowDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@ExperimentalPagerApi
@Composable
fun NewsSlider(
    articles: List<ArticleItem>,
    pagerState: PagerState,
    onNewsClick:(ArticleItem)->Unit
    ) {
    LaunchedEffect(Unit) {
        while(true) {
            yield()
            delay(2000)
            tween<Float>(600)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }
    Card(
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .height(230.dp)
            .fillMaxWidth()
            .padding(
                start = 10.dp, end = 10.dp, top = 10.dp
            )

    ) {

        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)) {
            HorizontalPager(
                count = articles.size,
                state = pagerState,
             ) { page ->
                NewsItemShape1(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(), articleItem = articles[page],
                onNewsClick=onNewsClick)
            }
            PagerIndicator(
                pagerState = pagerState,
                activeColor = YellowDark,
                inactiveColor = Color.White,
                activeIndicatorHeight = 8.dp,
                activeIndicatorWidth = 8.dp,
                inactiveIndicatorHeight = 8.dp,
                inactiveIndicatorWidth = 8.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            )
        }

    }


}


@ExperimentalPagerApi
@Composable
fun PagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    activeIndicatorHeight: Dp = 8.dp,
    activeIndicatorWidth: Dp = 8.dp,
    inactiveIndicatorHeight: Dp = 8.dp,
    inactiveIndicatorWidth: Dp = 8.dp,
    spacing: Dp = 8.dp,
    indicatorShape: Shape = CircleShape,
) {


    val activeIndicatorWidthPx = LocalDensity.current.run { activeIndicatorWidth.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val indicatorModifier = Modifier
                .size(width = inactiveIndicatorWidth, height = inactiveIndicatorHeight)
                .background(color = inactiveColor, shape = indicatorShape)

            repeat(pagerState.pageCount) {
                Box(indicatorModifier)
            }
        }

        Box(
            Modifier
                .offset {
                    val scrollPosition =
                        (pagerState.currentPage + pagerState.currentPageOffset)
                            .coerceIn(
                                0f,
                                (pagerState.pageCount - 1)
                                    .coerceAtLeast(0)
                                    .toFloat()
                            )
                    IntOffset(
                        x = ((spacingPx + activeIndicatorWidthPx)
                                * scrollPosition).toInt(),
                        y = 0
                    )
                }
                .size(width = activeIndicatorWidth, height = activeIndicatorHeight)
                .background(
                    color = activeColor,
                    shape = indicatorShape,
                )
        )
    }
}

