package azmithabet.com.news.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import azmithabet.com.news.data.model.artical.ArticlesItem
import azmithabet.com.news.data.model.artical.Source
import com.google.common.truth.Truth
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var articleDao: ArticleDao
    private lateinit var appDatabase: Database


    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), Database::class.java
        )
            .build()
        articleDao = appDatabase.getArticleDao()
    }



    @Test
    fun saveArticleTest() = runBlocking {
        // in source object must name some id to test because converter return name in id and name
       val testArticle= ArticlesItem(1,"oct 13, 2021","azmi",
            "http://azmi.com/11111.png","",
            Source("yallakora","yallakora"),"test",
            "https://www.yallakora.com/egyptian-league/2730/News/415930","content")

        articleDao.insertArticle(testArticle)
        val elements = articleDao.getAllArticles().take(1).toList()

        Truth.assertThat(elements[0][0]).isEqualTo(testArticle)
    }

    @Test
    fun deleteArticleTest() = runBlocking {
        // in source object must name some id to test because converter return name in id and name
        val testArticle= ArticlesItem(1,"oct 13, 2021","azmi",
            "http://azmi.com/11111.png","",
            Source("yallakora","yallakora"),"test",
            "https://www.yallakora.com/egyptian-league/2730/News/415930","content")

        articleDao.insertArticle(testArticle)
        articleDao.deleteArticle(testArticle)

        val elements = articleDao.getAllArticles().take(1).toList()

        Truth.assertThat(elements[0]).isEmpty()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}