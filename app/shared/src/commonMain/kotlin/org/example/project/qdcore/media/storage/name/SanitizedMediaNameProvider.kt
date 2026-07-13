package org.example.project.qdcore.media.storage.name

import org.example.project.qdcore.media.LocalMedia
import org.example.project.qdcore.media.RemoteMedia
import org.example.project.qdcore.util.sanitizeFileName

/**
 * A media name generator that sanitizes the file name and includes a unique identifier in it.
 * For example, "path/to/my file.jpg" is mapped to "my-file@HASH.jpg"
 */
class SanitizedMediaNameProvider : MediaNameProviderStrategy {
    private fun String.sanitize() = this.sanitizeFileName(replacement = "-")

    // Local media are given a unique identifier based on their file name and hash code.
    override fun visit(media: LocalMedia): String {
        val fileName = media.filePath.substringAfterLast('/') // Gets "image.png"
        val nameWithoutExtension = fileName.substringBeforeLast('.') // Gets "image"
        val extension = fileName.substringAfterLast('.', missingDelimiterValue = "") // Gets "png"

        return buildString {
            append(nameWithoutExtension)
            append("@")
            append(media.filePath.hashCode()) // hashCode() works natively on Strings!
            if (extension.isNotEmpty()) {
                append(".")
                append(extension)
            }
        }.sanitize()
    }

    // URLs are already unique, and they don't need an additional identifier.
    override fun visit(media: RemoteMedia) = media.url.sanitize() // url is already a string!
}