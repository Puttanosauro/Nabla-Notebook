package org.example.project.qdcore.media

/**
 * A media stored remotely.
 * @param url the URL string where the media is stored
 */
data class RemoteMedia(
    val url: String,
) : Media {

    init {
        /**
         * Init used to make sure that the string is a valid URL type
         */
        require(isValidUrl(url)) { "Invalid Remote Media URL: $url" }
    }

    override fun <T> accept(visitor: MediaVisitor<T>): T = visitor.visit(this)

    companion object {
        private val urlRegex = Regex("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")

        fun isValidUrl(url: String): Boolean {
            return urlRegex.matches(url)
        }
    }
}