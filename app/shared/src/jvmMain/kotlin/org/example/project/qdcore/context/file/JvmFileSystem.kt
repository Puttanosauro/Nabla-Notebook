package org.example.project.qdcore.context.file

import java.io.File

/**
 * JVM-specific implementation of the FileSystem.
 * Allows the Ktor server (and Desktop app) to utilize java.io.File
 */
class JvmFileSystem(
    override val workingDirectory: String? = null,
    override val root: FileSystem? = null,
) : FileSystem {

    override fun branch(workingDirectory: String?): FileSystem =
        JvmFileSystem(workingDirectory, root ?: this)

    override fun resolve(path: String): String {
        return if (workingDirectory != null) {
            File(workingDirectory, path).absolutePath
        } else {
            File(path).absolutePath
        }
    }

    override fun relativePathTo(other: FileSystem): String? {
        val fromDir = this.workingDirectory ?: return null
        val toDir = other.workingDirectory ?: return null
        return try {
            File(fromDir).toPath().relativize(File(toDir).toPath()).toString()
        } catch (e: IllegalArgumentException) {
            null
        }
    }


    override fun writeText(path: String, content: String) {
        val file = File(path)
        file.parentFile?.mkdirs() // Ensure parent folders exist
        file.writeText(content)
    }

    override fun writeBytes(path: String, bytes: ByteArray) {
        val file = File(path)
        file.parentFile?.mkdirs()
        file.writeBytes(bytes)
    }

    override fun copy(sourcePath: String, targetPath: String) {
        val source = File(sourcePath)
        val target = File(targetPath)
        target.parentFile?.mkdirs()

        if (source.isDirectory) {
            source.copyRecursively(target, overwrite = true)
        } else {
            source.copyTo(target, overwrite = true)
        }
    }

    override fun mkdirs(path: String) {
        File(path).mkdirs()
    }

    fun exists(path: String): Boolean {
        return File(path).exists()
    }

    fun readText(path: String): String {
        return File(path).readText()
    }
}