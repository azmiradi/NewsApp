package azmithabet.com.news.data.model.artical

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Entity(tableName = "articles",indices = [Index(value = ["url"], unique = true)])
@Parcelize
data class ArticleItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("publishedAt")
    val publishedAt: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("urlToImage")
    val urlToImage: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("source")
    val source: Source? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("content")
    val content: String? = null
) : Serializable, Parcelable
