package org.example.project.qdcore.media.export

actual fun downloadRemoteMediaBytes(url: String): ByteArray {
    // TODO (Web): Browsers will just use the URL string directly in the <img> tag!
    // We don't need to waste RAM downloading it here.
    return byteArrayOf(0)
}