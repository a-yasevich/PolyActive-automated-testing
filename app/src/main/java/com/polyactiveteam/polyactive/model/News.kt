package com.polyactiveteam.polyactive.model

data class News(
    val imageId: Int,
    val header: String,
    val newsDescription: String,
    val date: Long,
    val likeCounter: Int


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as News

        if (header != other.header) return false
        if (newsDescription != other.newsDescription) return false

        return true
    }

    override fun hashCode(): Int {
        var result = header.hashCode()
        result = 31 * result + newsDescription.hashCode()
        return result
    }
}
