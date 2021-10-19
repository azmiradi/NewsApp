package azmithabet.com.testapp.data.api
import azmithabet.com.news.data.api.ApiServices
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServicesTest {

    private lateinit var service: ApiServices
    private lateinit var server: MockWebServer
    private val country: String = "eg"
    private var category: String = "general"
    private var page: Int = 1
    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)


    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }

    @Test
    fun getArticlesFromAPITest(){
        runBlocking {
            enqueueMockResponse("newsapi.json")
            val responseBody = service.getNews(country, page, category).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=eg&page=1&category=general&apiKey=c045de1344e24899bffd2bb6d9c08bd5")
        }
    }

    @Test
    fun getArticlesSizeTest(){
        runBlocking {
            enqueueMockResponse("newsapi.json")
            val responseBody = service.getNews(country, page, category).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList!!.size).isEqualTo(4)
        }
    }

    @Test
    fun getArticlesCorrectContent(){
        runBlocking {
            enqueueMockResponse("newsapi.json")
            val responseBody = service.getNews(country, page, category).body()
            val articlesList = responseBody!!.articles
            val articlesItem = articlesList!![0]
            assertThat(articlesItem!!.url).isEqualTo("https://www.youm7.com/story/2021/10/19/أحمد-رزق-عن-فيلم-ريش-وضعنى-فى-مود-سيئ-وماحبتوش/5501283")
            assertThat(articlesItem.title).isEqualTo("أحمد رزق عن فيلم \"ريش\": وضعنى فى مود سيئ وماحبتوش ومصر كبيرة - اليوم السابع")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}