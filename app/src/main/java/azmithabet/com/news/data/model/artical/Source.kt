package azmithabet.com.news.data.model.artical

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Source(

    @SerializedName("name")
    val name: String?="",
    @SerializedName("id")
    val id: String?=""
) : Parcelable
