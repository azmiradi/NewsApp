package azmithabet.com.news.data.util

import android.content.Context
import android.os.Parcelable
import android.text.format.DateUtils
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.azmithabet.restaurantsguide.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun <T> MutableState<DataState<T>>.resetState() {
    this.value = DataState(
        isLoading = false,
        data = null,
        error = ""
    )
}

fun ImageRequest.Builder.imageBuilder(): ImageRequest.Builder {
    memoryCachePolicy(policy = CachePolicy.ENABLED)
    placeholder(R.drawable.logo)
    error(R.drawable.not_found)
    return this
}

fun String.formatDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    return try {
        val time: Long = sdf.parse(this)?.time ?: 0
        val now = System.currentTimeMillis()
        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS).toString()
    } catch (e: ParseException) {
        println("ParseException:::" + e.message)
        this
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun NavController.navigateWithArgs(
    route: String,
    vararg args: Pair<String, Parcelable>
) {
    navigate(route)

    requireNotNull(currentBackStackEntry?.arguments).apply {
        args.forEach { (key: String, arg: Parcelable) ->
            putParcelable(key, arg)
        }
    }
}

inline fun <reified T : Parcelable> NavBackStackEntry.requiredArg(key: String): T {
    return requireNotNull(arguments) { "arguments bundle is null" }.run {
        requireNotNull(getParcelable(key)) { "argument for $key is null" }
    }
}