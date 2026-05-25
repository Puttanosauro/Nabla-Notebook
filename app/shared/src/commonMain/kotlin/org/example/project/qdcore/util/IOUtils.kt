package org.example.project.qdcore.util

/**
 * Utility methods for virtual and physical path operations.
 */
object IOUtils {
    fun resolvePath(path: String, workingDirectory: String?): String {
        if (workingDirectory == null || isAbsolute(path)) {
            return normalize(path)
        }
        return normalize("$workingDirectory/$path")
    }

    fun isSubPath(parent: String, child: String): Boolean {
        val normalizedParent = normalize(parent).removeSuffix("/") + "/"
        val normalizedChild = normalize(child).removeSuffix("/") + "/"
        return normalizedChild.startsWith(normalizedParent)
    }

    private fun isAbsolute(path: String): Boolean {
        return path.startsWith("/") || path.matches(Regex("^[a-zA-Z]:\\\\.*"))
    }

    fun normalize(path: String): String {
        val parts = path.replace("\\", "/").split("/")
        val resolved = mutableListOf<String>()

        for (part in parts) {
            when (part) {
                "", "." -> continue
                ".." -> if (resolved.isNotEmpty()) resolved.removeLast()
                else -> resolved.add(part)
            }
        }

        val prefix = if (path.startsWith("/")) "/" else ""
        return prefix + resolved.joinToString("/")
    }
}

expect fun computeChecksum(path: String): String