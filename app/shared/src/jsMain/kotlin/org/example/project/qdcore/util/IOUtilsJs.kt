package org.example.project.qdcore.util

actual fun computeChecksum(path: String): String {
    return "JS_VIRTUAL_STATE_${path.hashCode()}"
}