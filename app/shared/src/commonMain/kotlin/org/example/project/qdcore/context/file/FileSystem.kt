package org.example.project.qdcore.context.file

/**
 * A file system abstraction which can retrieve files,
 * either absolutely or relative to a working directory.
 */
interface FileSystem {
    /**
     * The working directory string of this file system.
     */
    val workingDirectory: String?

    val root: FileSystem?

    val isRoot: Boolean
        get() = root == null

    /**
     * Resolves a local string path, either absolutely or relatively from [workingDirectory].
     */
    fun resolve(path: String): String

    fun branch(workingDirectory: String?): FileSystem

    /**
     * Computes the relative string path from this file system's [workingDirectory] to [other]'s.
     */
    fun relativePathTo(other: FileSystem): String?

    // --- Actions ---
    fun writeText(path: String, content: String)
    fun writeBytes(path: String, bytes: ByteArray)
    fun copy(sourcePath: String, targetPath: String)
    fun mkdirs(path: String)
}

/**
 * A simple [FileSystem] implementation that resolves paths
 * based on pure String manipulation, safe for KMP.
 */
internal data class SimpleFileSystem(
    override val workingDirectory: String? = null,
    override val root: FileSystem? = null,
) : FileSystem {

    override fun branch(workingDirectory: String?): FileSystem =
        SimpleFileSystem(workingDirectory, root ?: this)

    override fun resolve(path: String): String {
        if (workingDirectory == null) return path
        if (path.startsWith("/")) return path // Already absolute

        // Simple string concatenation for virtual paths
        val dir = if (workingDirectory.endsWith("/")) workingDirectory else "$workingDirectory/"
        return "$dir$path"
    }

    override fun relativePathTo(other: FileSystem): String? {
        val from = this.workingDirectory ?: return null
        val to = other.workingDirectory ?: return null

        if (from == to) return ""

        // Pure Kotlin string math to replace Java's Path.relativize()
        val fromParts = from.trim('/').split("/")
        val toParts = to.trim('/').split("/")

        var commonPrefixLength = 0
        val minLength = minOf(fromParts.size, toParts.size)
        while (commonPrefixLength < minLength && fromParts[commonPrefixLength] == toParts[commonPrefixLength]) {
            commonPrefixLength++
        }

        val upDirs = fromParts.size - commonPrefixLength
        val upString = "../".repeat(upDirs)
        val downString = toParts.drop(commonPrefixLength).joinToString("/")

        return "$upString$downString".removeSuffix("/")
    }


    override fun writeText(path: String, content: String) {
        throw UnsupportedOperationException("SimpleFileSystem cannot perform physical I/O.")
    }

    override fun writeBytes(path: String, bytes: ByteArray) {
        throw UnsupportedOperationException("SimpleFileSystem cannot perform physical I/O.")
    }

    override fun copy(sourcePath: String, targetPath: String) {
        throw UnsupportedOperationException("SimpleFileSystem cannot perform physical I/O.")
    }

    override fun mkdirs(path: String) {
        throw UnsupportedOperationException("SimpleFileSystem cannot perform physical I/O.")
    }
}