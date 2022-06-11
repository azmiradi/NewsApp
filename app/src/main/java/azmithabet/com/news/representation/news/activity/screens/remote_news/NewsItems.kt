package azmithabet.com.news.representation.news.activity.screens.remote_news

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.data.util.formatDate
import azmithabet.com.news.data.util.imageBuilder
import azmithabet.com.news.representation.theme.Typography
import azmithabet.com.news.representation.theme.RED_CUSTOM
import azmithabet.com.news.representation.theme.TeaGreen
import coil.compose.rememberImagePainter
import com.azmithabet.circleimageviewcompose.CircleImage
import com.ireward.htmlcompose.HtmlText

@Composable
fun NewsItemShape1(
    modifier: Modifier,
    articleItem: ArticleItem,
    isLocal:Boolean=false,
    onNewsClick: (ArticleItem) -> Unit,
    onDeleteLocal:((ArticleItem)->Unit)?=null
) {
    val time = rememberSaveable {
        articleItem.publishedAt?.formatDate() ?: ""
    }
    Card(
        modifier = modifier
            .clickable {
                onNewsClick(articleItem)
            },
        elevation = 10.dp,
        shape = RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = size.height / 4,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
                        }
                    },
                painter = rememberImagePainter(articleItem.urlToImage)
                {
                    imageBuilder()
                },
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 20.dp, end = 15.dp,
                        bottom = 20.dp
                    )
            ) {
                Text(
                    text = articleItem.title ?: "Azmi radi win with case mis thnks",
                    style = Typography.body1,
                    maxLines = 3,
                    lineHeight = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = time,
                    maxLines = 1,
                    color = Color.White,
                    style = Typography.body2
                )

            }

            if (isLocal) {
                IconButton(onClick = {
                    onDeleteLocal?.invoke(articleItem)
                } ,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(15.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        tint = RED_CUSTOM,
                    )
                }

            }
        }

    }
}


@Composable
fun NewsItemShape2(
    articleItem: ArticleItem,
    onNewsClick: (ArticleItem) -> Unit
) {

    Card(
        Modifier
            .width(300.dp)
            .height(100.dp)
            .clickable {
                onNewsClick(articleItem)
            },
        backgroundColor = TeaGreen,
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(
                    start = 10.dp, end = 10.dp,
                    bottom = 10.dp, top = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleImage(
                painter = rememberImagePainter(articleItem.urlToImage)
                {
                    imageBuilder()
                },
                contentDescription = "",
                size = 70.dp,
                borderStroke = BorderStroke(1.dp, Color.White)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp
                    )
            ) {
                HtmlText(
                    text = articleItem.author ?: articleItem.source?.name ?: "",
                    linkClicked = { link ->
                        Log.d("linkClicked", link)
                    }, style = Typography.body1,
                    maxLines = 1

                )
                Text(
                    text = articleItem.title ?: " ",
                    style = Typography.body2,
                    maxLines = 3,
                    lineHeight = 20.sp,
                )


            }
        }

    }
}

