package azmithabet.com.news.representation.news.activity.screens.news_details

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import azmithabet.com.news.data.model.artical.ArticleItem
import azmithabet.com.news.data.util.showToast
import azmithabet.com.news.representation.news.activity.screens.remote_news.HeaderTitle
import azmithabet.com.news.representation.theme.BlueDark
import com.azmithabet.restaurantsguide.R
import kotlinx.coroutines.launch

@Composable
fun NewsDetails(
    articleItem: ArticleItem,
    viewModel: NewsDetailsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var rememberWebViewProgress: Int by remember { mutableStateOf(-1) }


    Column(
        Modifier
            .fillMaxWidth()

    ) {
        HeaderTitle(string = articleItem.title.toString())
        Box(Modifier.fillMaxSize()) {

            CustomWebView(
                modifier = Modifier.fillMaxSize(),
                url = articleItem.url.toString(),
                onProgressChange = { progress ->
                    rememberWebViewProgress = progress
                },
                initSettings = { settings ->
                    settings?.apply {
                        setSupportMultipleWindows(true)
                        domStorageEnabled = true
                        databaseEnabled = true
                        loadsImagesAutomatically = true
                        useWideViewPort = true
                        loadWithOverviewMode = true
                        setSupportZoom(true)
                        builtInZoomControls = true
                        javaScriptCanOpenWindowsAutomatically = true
                        cacheMode = WebSettings.LOAD_NO_CACHE
                    }
                }, onBack = { webView ->
                    if (webView?.canGoBack() == true) {
                        webView.goBack()
                    } else {
                        onBack()
                    }

                }, onReceivedError = {

                }
            )
            FloatingActionButton(
                onClick = {
                    viewModel.saveArticle(articleItem) {
                        context.showToast(it)
                    }
                },
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp), backgroundColor = BlueDark
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.saved_icon),
                    contentDescription = "",
                    tint = White
                )
            }

            LinearProgressIndicator(
                progress = rememberWebViewProgress * 1.0F / 100F,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (rememberWebViewProgress == 100) 0.dp else 10.dp),
                color = Yellow
            )
        }
    }


}

@Composable
fun CustomWebView(
    modifier: Modifier = Modifier,
    url: String,
    onBack: (webView: WebView?) -> Unit,
    onProgressChange: (progress: Int) -> Unit = {},
    initSettings: (webSettings: WebSettings?) -> Unit = {},
    onReceivedError: (error: WebResourceError?) -> Unit = {}
) {
    val webViewChromeClient = remember {
        object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                onProgressChange(newProgress)
                super.onProgressChanged(view, newProgress)
            }
        }
    }
    val webViewClient = remember {
        object : WebViewClient() {
            override fun onPageStarted(
                view: WebView?, url: String?,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                onProgressChange(-1)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                onProgressChange(100)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if (null == request?.url) return false
                val showOverrideUrl = request.url.toString()
                try {
                    if (!showOverrideUrl.startsWith("http://")
                        && !showOverrideUrl.startsWith("https://")
                    ) {
                        Intent(Intent.ACTION_VIEW, Uri.parse(showOverrideUrl)).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            view?.context?.applicationContext?.startActivity(this)
                        }
                        return true
                    }
                } catch (e: Exception) {
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                onReceivedError(error)
            }
        }
    }
    var webView: WebView? = remember {
        null
    }
    val coroutineScope = rememberCoroutineScope()
    AndroidView(modifier = modifier, factory = { ctx ->
        WebView(ctx).apply {
            this.webViewClient = webViewClient
            this.webChromeClient = webViewChromeClient
            initSettings(this.settings)
            webView = this
            loadUrl(url)
        }
    })
    BackHandler {
        coroutineScope.launch {
            onBack(webView)
        }
    }
}