package azmithabet.com.news.data.model.artical

import com.google.gson.annotations.SerializedName

data class ApiResponse(

    @SerializedName("totalResults")
	val totalResults: Int? = null,

    @SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

    @SerializedName("status")
	val status: String? = null
)

