package org.example.project.qdcore.media

/**
 * A media that lives on the local filesystem.
 * @param filePath the string path to the local file where the media is stored
 */
data class LocalMedia(
    val filePath: String,
) : Media {
    override fun <T> accept(visitor: MediaVisitor<T>): T = visitor.visit(this)
}