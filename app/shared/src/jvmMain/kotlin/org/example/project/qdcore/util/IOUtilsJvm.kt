package org.example.project.qdcore.util

import java.io.File
import java.security.MessageDigest

actual fun computeChecksum(path: String): String {
    val file = File(path)
    if (!file.exists()) return ""

    val digest = MessageDigest.getInstance("SHA-256")

    if (file.isFile) {
        digest.update(file.readBytes())
    } else {
        file.walkTopDown()
            .filter { it.isFile }
            .sortedBy { it.relativeTo(file).path }
            .forEach {
                digest.update(it.relativeTo(file).path.toByteArray())
                digest.update(0)
                digest.update(it.length().toString().toByteArray())
                digest.update(0)
            }
    }
    return digest.digest().joinToString("") { "%02x".format(it) }
}