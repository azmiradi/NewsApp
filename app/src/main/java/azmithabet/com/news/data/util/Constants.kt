package azmithabet.com.news.data.util

object Constants {
    const val SUCCESS_CODE=200
    const val OK="ok"
    const val EGYPT="eg"
}

enum class NewsCategories(val category: String) {
    Business("business"),
    Entertainment("entertainment"),
    General("general"),
    Health("health"),
    Sports("Sports"),
}
