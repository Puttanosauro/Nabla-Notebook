package org.example.project.qdcore.media

/**
 * A generic media that is yet to be resolved to a [Media] subclass.
 * @param path path to the media, either a file or a URL
 * @param workingDirectory string path to the directory to resolve the media from, in case the path is relative
 */
data class ResolvableMedia(
    private val path: String,
    private val workingDirectory: String? = null, // Replaced java.io.File!
) : Media {
    /**
     * The resolved media as a [LocalMedia] or [RemoteMedia].
     */
    private val resolved: Media by lazy(::resolve)

    /**
     * @return [LocalMedia] if the path is a file, [RemoteMedia] if the path is a URL
     */
    private fun resolve(): Media {
        // 1. Is it a URL
        if (RemoteMedia.isValidUrl(path)) {
            return RemoteMedia(path) // Path is passed as a pure string
        }

        // 2. It's a local file
        val resolvedFilePath = if (workingDirectory != null && !path.startsWith("/")) {
            // If it's a relative path, combine the working directory and the path cleanly
            val cleanDir = workingDirectory.removeSuffix("/")
            val cleanPath = path.removePrefix("/")
            "$cleanDir/$cleanPath"
        } else {
            path
        }

        return LocalMedia(resolvedFilePath)
    }

    override fun <T> accept(visitor: MediaVisitor<T>): T = resolved.accept(visitor)
}